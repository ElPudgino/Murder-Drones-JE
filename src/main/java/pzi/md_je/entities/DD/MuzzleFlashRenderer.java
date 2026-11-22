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

public class MuzzleFlashRenderer extends ChModelRenderer
{
    public float brightness;
    public MuzzleFlashRenderer(AnimModelBase modelBase) 
    {
        super(modelBase);
    }
    @Override
    public void render(float sc)
    {
        if (BloomQueue.cPass == BloomQueue.Pass.Bloom)
        {
            GlStateManager.color(brightness,brightness,brightness);
            super.render(sc);
            GlStateManager.color(1f, 1f, 1f);
        }    
    }
}
