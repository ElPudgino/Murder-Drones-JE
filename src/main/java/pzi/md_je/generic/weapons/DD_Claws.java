package pzi.md_je.generic.weapons;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.loading.SoundRegister;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_server.SUseWeaponPacket;
import pzi.md_je.util.Collision;
import pzi.md_je.util.DamageUtils;

/** Short-range innate melee; never uses the hotbar item's damage or effects. */
public class DD_Claws extends InnateWeapon<DDMorph, ModelEntityDD>
{
    public static final float range = 2.3f;
    public static final float width = 1.2f;
    public static final float damage = 18f;
    private int cooldown;
    private final int atkCooldown = 12;

    public DD_Claws(DDMorph morph) { super(morph); }

    @Override public String GetId() { return "claws"; }
    @Override 
    public int GetNumericId() {return 1;}

    @Override
    @SideOnly(Side.CLIENT)
    public void OnRenderPre(ModelEntityDD model)
    {
        model.hand_claw.isHidden = false;
        if (GetAnimator().IsActive(ModelEntityDD.dash)) GetAnimator().ContinueAnim(ModelEntityDD.dash_claw, 1f, 1f);
    }

    @Override 
    public void OnHandRenderPre(ModelEntityDD model)
    {
        model.hand_claw.isHidden = false;
        EntityAnimator animator = GetHandAnimator();
        if (!animator.IsActive(ModelEntityDD.claws_swing) && ModelEntityDD.claws_idle != null)
            animator.ContinueAnim(ModelEntityDD.claws_idle, 1f, 1f);
    }

    @Override 
    public void Update() 
    { 
        super.Update();
        if (cooldown > 0) cooldown--; 
    }

    @Override 
    public void OnLeftClickHold()
    {
        if (cooldown > 0) return;
        cooldown = atkCooldown;
        GetHandAnimator().StartAnim(ModelEntityDD.claws_swing, 2.2f, 1f);
        if (!GetAnimator().IsActive(ModelEntityDD.dash))
            GetAnimator().StartAnim(ModelEntityDD.claw, 2.2f, 1f);
        PacketHandler.INSTANCE.sendToServer(new SUseWeaponPacket(1, GetNumericId()));
    }
    
    @Override 
    public void OnActionServer(int action)
    {
        if (action == 1 && cooldown <= 1 && selected)
        {
            cooldown = atkCooldown;

            morph.owner.world.playSound(null, morph.owner.posX, morph.owner.posY, morph.owner.posZ,
            SoundRegister.slash_event, SoundCategory.PLAYERS, 0.6f, 1f);

            List<EntityLivingBase> trgs = Collision.RayCastEntities(morph.owner.world, 
                morph.owner.getPositionVector().add(0, morph.owner.getEyeHeight(), 0),
                morph.owner.getLookVec(), range, width);
            for (EntityLivingBase e : trgs)
            {
                if (e != null && e != morph.owner && !e.isDead && !e.getIsInvulnerable())
                {

                    DamageUtils.DamageNoIFrames(e, getDamageSource(), damage);
                }
            }
        }
    }
    protected DamageSource getDamageSource()
    {
        return new EntityDamageSource("dd_claw", morph.owner);
    }

    @Override 
    public void OnActionClient(int action)
    {
        if (!GetAnimator().IsActive(ModelEntityDD.dash))
            GetAnimator().StartAnim(ModelEntityDD.claw, 2.2f, 1f);
    }

    @Override 
    public void OnDeselect()
    {
        super.OnDeselect();
        if (morph.owner.world.isRemote)
            GetHandAnimator().StopAnim(ModelEntityDD.claws_swing);
    }
    static ResourceLocation crosshair = ResourcesHelper.GetGuiTexture("claws_crosshair.png");
    @Override 
    public ResourceLocation CrosshairTex() 
    {
        return crosshair;
    }
    public int CrosshairSize() 
    {
        return 11;
    }

    @Override public NBTTagCompound ToNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("cooldown", cooldown);
        return tag;
    }

    @Override public void FromNBT(NBTTagCompound tag)
    {
        cooldown = MathHelper.clamp(tag.getInteger("cooldown"), 0, 10);
    }
}
