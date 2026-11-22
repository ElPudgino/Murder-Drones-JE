package pzi.md_je.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase
{   
    public MixinEntityPlayer(World w)
    {
        super(w);
    }
    /*
    @Shadow(remap = true)
    public float height;
    @Shadow(remap = true)
    public int width;
    @Shadow(remap = true)
    public World world;
    @Shadow(remap = true)
    public abstract boolean isElytraFlying();
    @Shadow(remap = true)
    public abstract boolean isPlayerSleeping();
    @Shadow(remap = true)
    public abstract boolean isSneaking();
    @Shadow(remap = true)
    public abstract void setSize(float x, float y);
    @Shadow(remap = true)
    public abstract AxisAlignedBB getEntityBoundingBox();  */

    @Inject(method = "updateSize", at = @At("HEAD"), cancellable = true)
    private void updateSizeMixin(CallbackInfo ci) 
    {
        IMorph m = PlayerMorphs.GetMorph((EntityPlayer)(Object)this);
        if (m != null)
        {
            Vec2f size = m.GetSize();

            if (size.x != this.width || size.y != this.height)
            {
                AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
                axisalignedbb = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)size.x, axisalignedbb.minY + (double)size.y, axisalignedbb.minZ + (double)size.x);

                if (!this.world.collidesWithAnyBlock(axisalignedbb))
                {
                    this.setSize(size.x, size.y);
                }
            }
            ci.cancel();
        }
    }

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    public void getEyeHeightMixin(CallbackInfoReturnable<Float> cl)
    {
        IMorph m = PlayerMorphs.GetMorph((EntityPlayer)(Object)this);
        if (m != null)
        {
            float eh = m.GetEyeHeight();
            cl.setReturnValue(eh);
        }
    }

    @Inject(method = "getEntityBoundingBox", at = @At("HEAD"), cancellable = true)
    public void getEntityBoundingBoxMixin(CallbackInfoReturnable<AxisAlignedBB> cl)
    {
        IMorph m = PlayerMorphs.GetMorph((EntityPlayer)(Object)this);
        if (m != null) cl.setReturnValue(m.GetRenderBB());
    }

}