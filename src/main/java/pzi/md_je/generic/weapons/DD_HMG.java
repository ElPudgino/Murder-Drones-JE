package pzi.md_je.generic.weapons;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.entities.DD.ModelHMGDD;
import pzi.md_je.gui.DDGui;
import pzi.md_je.gui.PrettyGUI;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.loading.SoundRegister;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CParticlePacket;
import pzi.md_je.network.to_server.SUseWeaponPacket;
import pzi.md_je.particles.EffBulletHit;
import pzi.md_je.particles.HitDebris;
import pzi.md_je.particles.ParticleEffect;
import pzi.md_je.particles.ParticleUtils;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.util.Collision;
import pzi.md_je.util.math;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class DD_HMG extends InnateWeapon<DDMorph, ModelEntityDD>
{
    public static final int MagSize = 60;
    public static final float AmmoRegen = 2.5f;
    public static final float ticksPerBullet = 3f;
    public static final float range = 80f;
    public static final float baseDamage = 11f;
    public static final int ammoRegenDelay = 10;
    public int ammoLeft = 0;
    protected float ammoRegenCounter;
    protected float shotCdLeft;
    protected int ammoRegenCd;
    private float recoil;
    private float prevRecoil;
    private float previousRenderTime;
    private float temp;
    private float heatGlow;
    private float muzzleFlash;
    private float prevMuzzleFlash;
    private float previousHeatGlow;
    private boolean hasRenderTime;

    public DD_HMG(DDMorph o)
    {
        super(o);
    }

    @Override
    public String GetId() { return "hmg"; }
    @Override 
    public int GetNumericId() {return 2;}

    @Override
    public void OnRenderPre(ModelEntityDD model, float swing)
    {
        preRenderGeneral(model);
    }
    @Override 
    public void OnHandRenderPre(ModelEntityDD model, float swing)
    {
        preRenderGeneral(model);
        GetHandAnimator().ContinueAnim(ModelEntityDD.aim_idle, 1f, 1f);
        GlStateManager.pushMatrix();
        float dt = Minecraft.getMinecraft().getRenderPartialTicks();
        GlStateManager.translate(0, 0, (recoil * dt + (1.0f-dt) * prevRecoil) * 0.2f);
    }
    protected void preRenderGeneral(ModelEntityDD model)
    {
        model.hand_hmg.isHidden = false;
        ModelHMGDD.HMG.heatGlow = heatGlow * GraphicsUtils.GetPartialTick() + previousHeatGlow * (1f - GraphicsUtils.GetPartialTick());
        ModelHMGDD.muzzle_flash.brightness = muzzleFlash * GraphicsUtils.GetPartialTick() + prevMuzzleFlash * (1f - GraphicsUtils.GetPartialTick());
    }
    

    @Override
    public void OnHandRenderPost()
    {
        GlStateManager.popMatrix();
    }

    public void Update()
    {
        super.Update();
        if (ammoRegenCd <= 0)
        {
            ammoRegenCounter += AmmoRegen * 0.05f;
            int n = MathHelper.fastFloor(ammoRegenCounter);
            ammoLeft += n;
            if (ammoLeft > MagSize) ammoLeft = MagSize;
            ammoRegenCounter -= n;
        }
        ammoRegenCd--;
        
        prevRecoil = recoil;
        recoil = Math.max(recoil * 0.83f - 0.04f, 0f);
        temp = Math.max(temp - 0.18f, 0f);
        previousHeatGlow = heatGlow;
        heatGlow = MathHelper.clamp((temp - 3f) / 7f, 0f, 1f);
        prevMuzzleFlash = muzzleFlash;
        muzzleFlash = MathHelper.clamp(muzzleFlash - 0.4f, 0f, 1f);

        shotCdLeft = Math.max(0f, shotCdLeft - 1f / ticksPerBullet);
    }

    public void OnLeftClickHold()
    {
        if (shotCdLeft <= 0f && ammoLeft > 0)
        {
            ammoRegenCd = ammoRegenDelay;
            shotCdLeft = 1f;
            ammoLeft--;
            ShootClient(true);
        }
    }

    protected void ShootClient(boolean original)
    {
        recoil = Math.min(1.2f, recoil + 1f);
        temp = MathHelper.clamp(temp+1.2f,0,10f);
        muzzleFlash = 1f;
        Double dist = 0d;
        EntityLivingBase e = Collision.RaycastEntityFromHead(morph.owner, range, 0, dist);
        if (e == null)
        {
            RayTraceResult res = Collision.RaycastDir(morph.owner.world, 
                math.getHeadPos(morph.owner), 
                morph.owner.getLookVec(), range);
            if (res != null)
            {
                GetEffect(res).Spawn(morph.owner.world);   
            }
        }
        if (original) PacketHandler.INSTANCE.sendToServer(new SUseWeaponPacket(1, GetNumericId()));   
    }

    protected ParticleEffect GetEffect(RayTraceResult res)
    {
        Material mat = morph.owner.world.isBlockLoaded(res.getBlockPos()) ? morph.owner.world.getBlockState(res.getBlockPos()).getMaterial() : Material.AIR;
        int type = mat == Material.IRON || mat == Material.ANVIL ? 1 : 0;
        return new EffBulletHit(res.hitVec, math.faceVec(res.sideHit), type, MD.random.nextInt() % 4 + 5);
    }

    protected void ShootServer()
    {
        if (ammoLeft < 0 || shotCdLeft > 0.1f) return;
        ammoLeft--;
        shotCdLeft = 1f;
        ammoRegenCd = ammoRegenDelay;
        morph.owner.world.playSound(null, morph.owner.posX, morph.owner.posY, morph.owner.posZ,
            SoundRegister.shot_event, SoundCategory.PLAYERS, 0.6f, 1f);
        Double hitdist = 0d;
        EntityLivingBase e = Collision.RaycastEntityFromHead(morph.owner, range, 0f, hitdist);
        if (e == null)
        {
            return;
        }
        int ht = e.hurtResistantTime;
        e.hurtResistantTime = 0;
        e.attackEntityFrom(getDamageSource(), baseDamage);
        e.hurtResistantTime = ht;
    }
    protected DamageSource getDamageSource()
    {
        return new EntityDamageSource("hmg_projectile", morph.owner).setProjectile();
    }
    
    public void OnActionServer(int action)
    {
        if (selected && action == 1 && shotCdLeft <= 0f && ammoLeft > 0) ShootServer();
    }

    @Override 
    public void OnActionClient(int action)
    {
        // we trust the server, so no extra checks
        if (action == 1) ShootClient(false);
    }


    @Override 
    public void DrawGUI(PrettyGUI gui)
    {
        gui.drawString("Ammo: "+ammoLeft+"/"+DD_HMG.MagSize, 0.8f, 0.4f, -10, 0, 4f, DDGui.baseColor, false);
    }
    static ResourceLocation crosshair = ResourcesHelper.GetGuiTexture("hmg_crosshair.png");
    @Override 
    public ResourceLocation CrosshairTex() 
    {
        return crosshair;
    }
    public int CrosshairSize() 
    {
        return 1;
    }

    public NBTTagCompound ToNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("ammo", ammoLeft);
        tag.setFloat("regen", ammoRegenCounter);
        tag.setFloat("cooldown", shotCdLeft);
        return tag;
    }

    public void FromNBT(NBTTagCompound tag)
    {
        ammoLeft = MathHelper.clamp(tag.getInteger("ammo"), 0, MagSize);
        float regen = tag.getFloat("regen");
        float cooldown = tag.getFloat("cooldown");
        ammoRegenCounter = Float.isFinite(regen) ? MathHelper.clamp(regen, 0f, 1f) : 0f;
        shotCdLeft = Float.isFinite(cooldown) ? MathHelper.clamp(cooldown, 0f, 1f) : 0f;
    }
}
