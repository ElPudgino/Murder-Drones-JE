package pzi.md_je.rendering;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import pzi.md_je.MD;
import pzi.md_je.entities.EntityMD;

public class BloomQueue 
{
    public static Pass cPass = Pass.Normal;
    public static boolean renderedWorld;
    public static List<EntityBloomTicket> tickets = new ArrayList<>();
    public static int curQueueLength;
    public static void SubmitBloomTicket(RenderLiving<EntityMD> render, EntityMD entity,
        double x, double y, double z, float yaw, float partial)
    {
        if (renderedWorld) return;
        if (tickets.size() > curQueueLength)
        {
            tickets.get(curQueueLength).Set(render, entity, x, y, z, yaw, partial);
        }
        else
        {
            tickets.add(new EntityBloomTicket().Set(render, entity, x, y, z, yaw, partial));
        }
        curQueueLength++;
    }

    public static void RenderBloomPass()
    {
        cPass = Pass.Bloom;
        Minecraft mc = Minecraft.getMinecraft();

        mc.getFramebuffer().unbindFramebuffer();
        BloomFramebuffer.SyncDepth();


        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(0f, -10f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.ONE, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE);
        GlStateManager.depthMask(false);
        for (int i = 0; i < curQueueLength; i++)
        {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            tickets.get(i).doRender();
        }
        GlStateManager.depthMask(true);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        BloomFramebuffer.Unbind();
        mc.getFramebuffer().bindFramebuffer(true);

        curQueueLength = 0;
        cPass = Pass.Normal;
    }
    
    public enum Pass
    {
        Normal,
        Bloom
    }
}
