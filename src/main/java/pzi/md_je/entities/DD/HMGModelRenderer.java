package pzi.md_je.entities.DD;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import pzi.md_je.MD;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.anim.ChModelRenderer;
import pzi.md_je.rendering.BloomQueue;

public class HMGModelRenderer extends ChModelRenderer
{
    public ResourceLocation texture;
    public ResourceLocation parentTexture;
    public ResourceLocation bloomTexture;
    public ResourceLocation heatGlowTexture;
    public float heatGlow;
    public ResourceLocation parentBloomTexture;
    public HMGModelRenderer(AnimModelBase modelBase) 
    {
        super(modelBase);
    }
    @Override
    public void render(float sc)
    {
        TextureManager tex = Minecraft.getMinecraft().renderEngine;
        if (BloomQueue.cPass == BloomQueue.Pass.Bloom)
        {
            tex.bindTexture(bloomTexture);
            super.render(sc);
            GlStateManager.color(heatGlow, heatGlow*0.6f, heatGlow*0.2f);
            tex.bindTexture(heatGlowTexture);
            super.render(sc*1.01f);
            tex.bindTexture(parentBloomTexture);
            GlStateManager.color(1f, 1f, 1f);
        }
        else
        {
            tex.bindTexture(texture);
            super.render(sc);
            tex.bindTexture(parentTexture);
        }      
    }
}
