package pzi.md_je.entities.DD;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.generic.IOilHolder;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.ability.dd.CDDTakeOffPacket;
import pzi.md_je.network.to_client.ability.dd.CDDToggleWingsPacket;
import pzi.md_je.network.to_server.ability.dd.SDDToggleWingsPacket;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.util.DamageUtils;
import pzi.md_je.util.math;

public class FlightDD 
{
    protected StatsDD stats; 

    private boolean flying;
    private boolean wingsOut;

    public Vec3d prevVel1;
    public Vec3d prevVel2;
    private final float rotAcc = 0.2f;
    private final float tangentAcc = 3f;
    private final float trgVel = 1.2f;

    public static final AxisAlignedBB wingsBB = new AxisAlignedBB(-1.4f, 2.2f, -1.4f, 1.4f, 3.2f, 1.4f);

    public FlightDD(StatsDD st)
    {
        stats = st;
        prevVel1 = Vec3d.ZERO;
        prevVel2 = Vec3d.ZERO;
    }

    public void Animate(ModelEntityDD model)
    {
        if (!flying) return;
        float dt = GraphicsUtils.GetPartialTick();

        Vec3d vel = new Vec3d(stats.owner.motionX, stats.owner.motionY, stats.owner.motionZ);
        Vec3d velinter = vel.scale(dt).add(prevVel1.scale(1d-dt));
        double curAngle = calcAngle(vel);

        double prevAngle = prevVel1 == null ? curAngle : calcAngle(prevVel1);
        model.root.rotateAngleX = (float)(curAngle * dt + (1-dt)*prevAngle);
        model.root.offsetY += 0.7f * Math.sin(GetFwdAngle(velinter));

        double sine = (vel.x * prevVel1.z - vel.z * prevVel1.x) / Math.sqrt(0.001f +(vel.x*vel.x + vel.z*vel.z)*(prevVel1.x*prevVel1.x + prevVel1.z*prevVel1.z));
        double prev_sine = (prevVel1.x * prevVel2.z - prevVel1.z * prevVel2.x) / Math.sqrt(0.001f +(prevVel2.x*prevVel2.x + prevVel2.z*prevVel2.z)*(prevVel1.x*prevVel1.x + prevVel1.z*prevVel1.z));
        double r = MathHelper.clamp(velinter.length() / trgVel,0,1);
        sine = sine * r;
        prev_sine = prev_sine * r;
        model.root.rotateAngleZ = (float)(sine * dt + (1-dt) * prev_sine) * 10;
    }

    private double calcAngle(Vec3d vel)
    {   
        double r = MathHelper.clamp(vel.length() / trgVel,0,1);
        double angle = Math.atan(vel.y / (Math.sqrt(vel.x*vel.x + vel.z*vel.z) + 0.01f));
        angle = Math.PI/2d - angle;
        return r * angle;
    }

    public double GetFwdAngle(Vec3d vel)
    {
        if (!flying) return 0d;
        double r = MathHelper.clamp(vel.length() / trgVel,0,1);
        double angle = Math.atan(vel.y / (Math.sqrt(vel.x*vel.x + vel.z*vel.z) + 0.01f));
        angle = Math.PI/2d - angle;
        return r * angle;
    }

    public void Update()
    {
        boolean dirty = false;
        if (flying && shouldStopFlying())
        {
            stopFlying();
            dirty = true;
        }
        if (wingsOut && shouldFoldWings())
        { 
            foldWings(); 
            dirty = true;
        }
        if (dirty && !stats.owner.world.isRemote) PacketHandler.INSTANCE.sendToAllTracking(new CDDToggleWingsPacket(stats.owner, areWingsOut(), isFlying()), stats.owner);   
        
        boolean a = stats.owner instanceof EntityPlayer && ((EntityPlayer)stats.owner).isSprinting();
        if (flying)
        {
            applyElytraPhysics(stats.owner.getLookVec(), a);
        }      
    }

    public void tryStartFlying()
    {
        if (!shouldStopFlying()) startFlying();
    }

    private void applyElytraPhysics(Vec3d targetDir, boolean thrust) // targetDir must be normalized
    {
        targetDir = targetDir.add(0, 0.1d, 0).normalize();
        Vec3d vel = new Vec3d(stats.owner.motionX, stats.owner.motionY, stats.owner.motionZ);
        
        prevVel2 = prevVel1;
        prevVel1 = vel;
 
        Vec3d acc = targetDir.subtract(vel.normalize());
        if (vel.length() > 0.1f)
            acc = acc.scale(rotAcc * vel.length() * 0.05f);
        else
            acc = acc.scale(0);

        vel = vel.add(acc);

        if (thrust)
        {
            vel = vel.add(targetDir.scale(tangentAcc * 0.05f));
        }
        if (thrust)
            vel = new Vec3d(vel.x*0.99f, vel.y*0.975f, vel.z*0.99f);
        else 
            vel = new Vec3d(vel.x * 0.95f, vel.y * 0.9f, vel.z * 0.95f);

        stats.owner.motionX = vel.x;
        stats.owner.motionY = vel.y;
        stats.owner.motionZ = vel.z;


        // Store motion for potential future use (e.g., smooth rotation).
    }

    public void SetWingState(boolean s)
    {
        if (wingsOut == s) return;
        if (s) unfoldWings();
        else foldWings();
    }
    public void SetFlyState(boolean s)
    {
        if (flying == s) return;
        if (s) startFlying();
        else stopFlying();
    }
    public void TrySetFlyState(boolean s)
    {
        if (flying == s) return;
        if (s) tryStartFlying();
        else stopFlying();
    }
    public void TrySetWingState(boolean s)
    {
        if (wingsOut == s) return;
        if (s) tryUnfoldWings();
        else foldWings();
    }

    protected void startFlying()
    {
        stats.owner.setNoGravity(true);
        stats.owner.fallDistance = 0f;
        flying = true;
    }

    public boolean shouldStopFlying()
    {
        return stats.owner.onGround || stats.owner.isInLava() || stats.criticalState
        || !areWingsOut() || stats.GetCurrentOil() < StatsDD.yellowOilLevel || stats.GetHeatManager().overheat == OverheatLevel.Red;
    }
    public void stopFlying()
    {
        stats.owner.setNoGravity(false);
        stats.owner.fallDistance = 0f;
        flying = false;
    }
    public void tryUnfoldWings()
    {
        if (!shouldFoldWings())
        {
            unfoldWings();   
        }
    }
    public void unfoldWings()
    {
        if (!wingsOut && stats.owner.world.isRemote)
        {
            stats.oMorph.GetAnimator().StartAnim(ModelEntityDD.unfold_wings, 2f, 1f);
            stats.oMorph.GetAnimator().StopAnim(ModelEntityDD.fold_wings);
        } 
        wingsOut = true;   
    }
    public boolean shouldFoldWings()
    {
        return stats.criticalState || stats.owner.world.checkBlockCollision(wingsBB.offset(stats.owner.getPositionVector()));
    }
    public void foldWings()
    {
        if (wingsOut && stats.owner.world.isRemote)
        {
            stats.oMorph.GetAnimator().StartAnim(ModelEntityDD.fold_wings, 2f, 1f);
            stats.oMorph.GetAnimator().StopAnim(ModelEntityDD.unfold_wings);
        }
        wingsOut = false;
    }
    public boolean canTakeOff()
    {
        return !stats.criticalState && wingsOut && stats.owner.onGround && stats.GetCurrentOil() >= StatsDD.yellowOilLevel && 
        stats.GetHeatManager().overheat.ordinal() <= OverheatLevel.White.ordinal();
    }
    public void tryTakeOff()
    {
        if (canTakeOff()) takeOff();
    }
    public void takeOff()
    {
        stats.owner.motionY += 4f;
        stats.heatManager.temp += StatsDD.TPerTakeOff;
        if (stats.owner.world.isRemote) stats.oMorph.GetAnimator().StartAnim(ModelEntityDD.take_off, 2f, 1f);
        if (!stats.owner.world.isRemote)
        {
            stats.Input(-StatsDD.takeOffCost);
            PacketHandler.INSTANCE.sendToAllTracking(new CDDTakeOffPacket(stats.owner), stats.owner);
        }   
    }
    public boolean isFlying()
    {
        return flying;
    }
    public boolean areWingsOut()
    {
        return wingsOut;
    }
    public NBTTagCompound ToNBT() 
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("airb", flying);
        tag.setBoolean("wgs", wingsOut);
        return tag;
    }
    public void FromNBT(NBTTagCompound tag)
    {
        flying = tag.getBoolean("airb");
        stats.owner.setNoGravity(flying);
        wingsOut = tag.getBoolean("wgs");
    }
}
