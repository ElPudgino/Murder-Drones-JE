package pzi.md_je.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import pzi.md_je.MD;

public class GraphicsUtils 
{
    public static float partial;
    public static float GetPartialTick()
    {
        if (BloomQueue.cPass == BloomQueue.Pass.Bloom) return partial;
        return Minecraft.getMinecraft().getRenderPartialTicks();
    }
    public static void copyDepth(Framebuffer src, Framebuffer dst) 
    {
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, src.framebufferObject);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dst.framebufferObject);
        GL30.glBlitFramebuffer(0, 0, src.framebufferWidth, src.framebufferHeight,
                0, 0, dst.framebufferWidth, dst.framebufferHeight,
                GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, dst.framebufferObject);
    }
    public static void GlSetColor(int colorhex, float brightness)
    {
        GlStateManager.color((colorhex >> 16) / 255f * brightness, (colorhex >> 8 & 0xff) / 255f * brightness, (colorhex & 0xff) / 255f * brightness);
    }
    public static void GlSetColor(int colorhex, float brightness, float alpha)
    {
        GlStateManager.color((colorhex >> 16) / 255f * brightness, (colorhex >> 8 & 0xff) / 255f * brightness, (colorhex & 0xff) / 255f * brightness, alpha);
    }
}
