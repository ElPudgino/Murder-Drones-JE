package pzi.md_je.entities.DD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import pzi.md_je.MD;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.entities.HandRender;
import pzi.md_je.generic.weapons.InnateWeapon;
import pzi.md_je.loading.EntityRenderHandler;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.rendering.BloomFramebuffer;
import pzi.md_je.rendering.BloomQueue;

public class HandRenderDD extends HandRender<EntityDDMorph, ModelEntityDD>
{
    
    @Override
    protected void PreAnimate(EntityDDMorph dummy, EntityPlayer owner, float swing)
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180f, 0, 0, 1);
        InnateWeapon<DDMorph, ModelEntityDD> wep = dummy.morph.GetWeapon(EnumHand.MAIN_HAND);
        ModelEntityDD m = GetModel();
        m.hand_normal.isHidden = true;
        m.hand_claw.isHidden = true;
        m.hand_hmg.isHidden = true;
        m.arm_r.setRotationPoint(0,0,0);
		AnimModelBase.setRotationAngle(m.arm_r, 0,0,0);
        wep.OnHandRenderPre(m, swing);
    }
    @Override
    protected void PostAnimate(EntityDDMorph dummy, EntityPlayer owner, float swing)
    {
        GetModel().renderRightArm(dummy);
        BloomFramebuffer.StartDrawBloomExt();
        GetModel().renderRightArm(dummy);
        BloomFramebuffer.EndDrawBloomExt();
        GlStateManager.popMatrix();
        InnateWeapon<DDMorph, ModelEntityDD> wep = dummy.morph.GetWeapon(EnumHand.MAIN_HAND);
        wep.OnHandRenderPost();
        ModelEntityDD m = GetModel();
        m.arm_r.setRotationPoint(-4.0F, -4.0654F, 1.4723F);
		AnimModelBase.setRotationAngle(m.arm_r, -0.2485F, 0.0905F, 0.2921F);
    }

    @Override
    public void checkModelCompiled(EntityDDMorph dummy)
    {
        if (EntityRenderHandler.DD != null) return;
        EntityRenderHandler.DD = new RenderEntityDD(Minecraft.getMinecraft().getRenderManager());
        GlStateManager.colorMask(false, false, false, false);
        EntityRenderHandler.DD.doRender(dummy, 0, 0, 0, 0, 0);
        GlStateManager.colorMask(true, true, true, true);
    }

    @Override
    public ModelEntityDD GetModel()
    {
        return (ModelEntityDD)EntityRenderHandler.DD.getMainModel();
    }
}
