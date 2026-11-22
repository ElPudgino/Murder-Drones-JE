package pzi.md_je.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.rendering.BloomFramebuffer;

public abstract class PrettyGUI extends Gui
{
    float scalex;
    float scaley;
    public void drawTexRect(float xanch, float yanch,int xoffs, int yoffs, float u, float v, int width, int height)
    {
        int xa = (int)(xanch * Minecraft.getMinecraft().displayWidth);
        int ya = (int)(yanch * Minecraft.getMinecraft().displayHeight);
        float scale = Math.min(scalex, scaley);
        drawModalRectWithCustomSizedTexture(xa + (int)(xoffs*scalex-width*2*scale), ya + (int)(yoffs*scaley-height*2*scale), 0, 0, (int)(width*4*scale*u), (int)(height*4*scale*v), (int)(width*4*scale), (int)(height*4*scale));
    }

    public void drawTexRectUncentered(float xanch, float yanch,int xoffs, int yoffs, int texw, int texh, int width, int height)
    {
        int xa = (int)(xanch * Minecraft.getMinecraft().displayWidth);
        int ya = (int)(yanch * Minecraft.getMinecraft().displayHeight);
        float scale = Math.min(scalex, scaley);
        drawModalRectWithCustomSizedTexture(xa + (int)(xoffs*scalex), ya + (int)(yoffs*scaley), 0, 0, (int)(4*scale*texw), (int)(4*scale*texh), (int)(width*4*scale), (int)(height*4*scale));
    }    

    public void drawString(String str, float xanch, float yanch, float xoffs, float yoffs, float text_scale, int color, boolean centered)
    {
        int xa = (int)(xanch * Minecraft.getMinecraft().displayWidth);
        int ya = (int)(yanch * Minecraft.getMinecraft().displayHeight);
        float scale = Math.min(scalex, scaley);
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        GlStateManager.pushMatrix();
        text_scale *= scale;
        GlStateManager.scale(text_scale, text_scale,1f);
        if (centered)
            fr.drawString(str, (xa + (int)(xoffs*scalex-fr.getStringWidth(str)*2*scale))/text_scale, (ya + (int)(yoffs*scaley-fr.FONT_HEIGHT*2*scale))/text_scale, color, false);
        else
            fr.drawString(str, (xa + (int)(xoffs*scalex))/text_scale, (ya + (int)(yoffs*scaley-fr.FONT_HEIGHT*2*scale))/text_scale, color, false);
        GlStateManager.popMatrix();
    }

    public void Render()
    {
        StartDraw();
        Draw();
        EndDraw();
    }
    public void Draw()
    {

    }
    public void StartDraw()
    {
        Minecraft mc = Minecraft.getMinecraft();
        int w = mc.displayWidth;
        int h = mc.displayHeight;
        scalex = w / 1920.0f;
        scaley = h / 1080.0f;

    }
    public static void PrepareDrawGUI()
    {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        
        GlStateManager.pushMatrix();

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, mc.displayWidth, mc.displayHeight, 0.0D, 1000.0D, 3000.0D);

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }
    public static void EndDrawGUI()
    {
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();

        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(
        GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
        GlStateManager.SourceFactor.ONE,       GlStateManager.DestFactor.ZERO);

        GlStateManager.enableBlend();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.bindTexture(0);
    }
    public void EndDraw()
    {

    }

    public void StartDrawBloom()
    {
        Framebuffer mcb = Minecraft.getMinecraft().getFramebuffer();

        mcb.unbindFramebuffer();

        BloomFramebuffer.Bind();
    }

    public void EndDrawBloom()
    {
        BloomFramebuffer.Unbind();

        Framebuffer mcb = Minecraft.getMinecraft().getFramebuffer();
        mcb.bindFramebuffer(true);
    }
    public void CleanUp()
    {
        
    }
    public abstract boolean HasGuiGlow();
    public static boolean NeedGuiGlow() // run on client to check if we need the second gui bloom pass
    {
        EntityPlayer p = Minecraft.getMinecraft().player;
        IMorph m = PlayerMorphs.GetMorph(p);
        return m != null && m.GetGui().HasGuiGlow();
    }
}