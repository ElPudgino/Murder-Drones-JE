package pzi.md_je.rendering;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.rendering.BloomQueue.Pass;

@SideOnly(Side.CLIENT)
public class BloomFramebuffer
{
    // Capture/depth stay full resolution. Only the color blur is downsampled.
    private static Framebuffer emission;
    private static Framebuffer blurA;
    private static Framebuffer blurB;
    private static Framebuffer sceneCopy;
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static void ensureBuffers()
    {
        Framebuffer scene = mc.getFramebuffer();
        int width = Math.max(1, scene.framebufferWidth);
        int height = Math.max(1, scene.framebufferHeight);
        if (emission != null && emission.framebufferWidth == width
                && emission.framebufferHeight == height
                && emission.isStencilEnabled() == scene.isStencilEnabled()) return;

        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (emission != null) emission.deleteFramebuffer();
        if (blurA != null) blurA.deleteFramebuffer();
        if (blurB != null) blurB.deleteFramebuffer();
        if (sceneCopy != null) sceneCopy.deleteFramebuffer();
        emission = createBuffer(width, height, true, scene.isStencilEnabled());
        // Round up so odd sizes and a minimized 1x1 window remain valid.
        int halfWidth = Math.max(1, (width + 1) / 2);
        int halfHeight = Math.max(1, (height + 1) / 2);
        blurA = createBuffer(halfWidth, halfHeight, false, false);
        blurB = createBuffer(halfWidth, halfHeight, false, false);
        sceneCopy = createBuffer(width, height, false, false);
    }

    private static Framebuffer createBuffer(int width, int height, boolean depth, boolean stencil)
    {
        Framebuffer result = new Framebuffer(width, height, depth);
        if (stencil) result.enableStencil();
        result.setFramebufferFilter(GL11.GL_LINEAR);
        result.bindFramebufferTexture();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        result.unbindFramebufferTexture();
        result.setFramebufferColor(0, 0, 0, 0);
        result.framebufferClear();
        return result;
    }

    public static int GetWidth() { return emission.framebufferWidth; }
    public static int GetHeight() { return emission.framebufferHeight; }

    public static void Bind()
    {
        ensureBuffers();
        emission.bindFramebuffer(true);
    }

    public static void Unbind() { emission.unbindFramebuffer(); }
    public static void BindTex() { emission.bindFramebufferTexture(); }
    public static void UnbindTex() { emission.unbindFramebufferTexture(); }

    public static void SyncDepth()
    {
        ensureBuffers(); 
        GraphicsUtils.copyDepth(mc.getFramebuffer(), emission);
    }

    public static void Blur()
    {
        try (PostState ignored = new PostState())
        {
            ensureBuffers();
            preparePost();
            blurColor();
        }
    }

    private static void preparePost()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.colorMask(true, true, true, true);
    }

    private static void blurColor()
    {
        int downsample = ShaderUtils.Downsample;
        ShaderUtils.EnableShaderProg(downsample);
        ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(downsample, "DiffuseSampler"), 0);
        ARBShaderObjects.glUniform2fARB(ARBShaderObjects.glGetUniformLocationARB(downsample, "InSize"),
                emission.framebufferWidth, emission.framebufferHeight);
        emission.bindFramebufferTexture();
        blurA.bindFramebuffer(true); 
        DrawToScreen();
        ShaderUtils.DisableShaderProg();

        float minScale = Math.min(mc.displayWidth / 1920f, mc.displayHeight / 1080f);
        
        int blur = ShaderUtils.Blur;
        ShaderUtils.EnableShaderProg(blur);
        int sampler = ARBShaderObjects.glGetUniformLocationARB(blur, "DiffuseSampler");
        int size = ARBShaderObjects.glGetUniformLocationARB(blur, "InSize");
        int direction = ARBShaderObjects.glGetUniformLocationARB(blur, "BlurDir");
        ARBShaderObjects.glUniform1iARB(sampler, 0);
        ARBShaderObjects.glUniform2fARB(size, blurA.framebufferWidth, blurA.framebufferHeight);
        ARBShaderObjects.glUniform2fARB(direction, 1.08f * minScale, 0);
        blurA.bindFramebufferTexture();
        blurB.bindFramebuffer(true);
        DrawToScreen();
        ARBShaderObjects.glUniform2fARB(direction, 0, 1.08f * minScale);
        blurB.bindFramebufferTexture();
        blurA.bindFramebuffer(true);
        DrawToScreen();
        ShaderUtils.DisableShaderProg();
    }

    public static void StartDrawBloomExt()
    {
        BloomQueue.cPass = Pass.Bloom;
        mc.getFramebuffer().unbindFramebuffer();
        Bind();
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.clearColor(0, 0, 0, 0);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(0, -4f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }

    public static void EndDrawBloomExt()
    {
        GlStateManager.disablePolygonOffset();
        Unbind();
        mc.getFramebuffer().bindFramebuffer(true);
        BloomQueue.cPass = Pass.Normal;
    }

    public static void DrawToScreen()
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, mc.displayHeight, 0).tex(0, 0).endVertex();
        buffer.pos(mc.displayWidth, mc.displayHeight, 0).tex(1, 0).endVertex();
        buffer.pos(mc.displayWidth, 0, 0).tex(1, 1).endVertex();
        buffer.pos(0, 0, 0).tex(0, 1).endVertex();
        tessellator.draw();
    }

    public static void DrawAsBloom()
    {
        try (PostState ignored = new PostState())
        {
            ensureBuffers();
            preparePost();
            Framebuffer scene = mc.getFramebuffer();
            // A stable background source: never sample the attachment being written.
            GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, scene.framebufferObject);
            GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, sceneCopy.framebufferObject);
            GL30.glBlitFramebuffer(0, 0, scene.framebufferWidth, scene.framebufferHeight,
                    0, 0, sceneCopy.framebufferWidth, sceneCopy.framebufferHeight,
                    GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);

            blurColor();
            int bloom = ShaderUtils.Bloom;
            ShaderUtils.EnableShaderProg(bloom);
            float minScale = Math.min(mc.displayWidth / 1920f, mc.displayHeight / 1080f);
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "intensive"), 3f);
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "base"), 0.07f);
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "threshold_up"), 0.5f);
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "threshold_down"), 0.15f);
            ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "BloomSampler"), 0);
            ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "EmissionSampler"), 1);
            ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(bloom, "BackgroundSampler"), 3);
            GlStateManager.setActiveTexture(GL13.GL_TEXTURE3);
            sceneCopy.bindFramebufferTexture();
            GlStateManager.setActiveTexture(GL13.GL_TEXTURE1);
            emission.bindFramebufferTexture();
            GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
            blurA.bindFramebufferTexture();
            scene.bindFramebuffer(true); // Restore full-size viewport for final composition.
            GlStateManager.colorMask(true, true, true, false);
            DrawToScreen();
            ShaderUtils.DisableShaderProg();

            // Keep the original sharp mask separate from the halo; clear for next frame.
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.depthMask(true);
            emission.framebufferClear();
        }
    }

    private static final class PostState implements AutoCloseable
    {
        private final int active = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
        private final int program = ARBShaderObjects.glGetHandleARB(ARBShaderObjects.GL_PROGRAM_OBJECT_ARB);
        private final int read = GL11.glGetInteger(GL30.GL_READ_FRAMEBUFFER_BINDING);
        private final int draw = GL11.glGetInteger(GL30.GL_DRAW_FRAMEBUFFER_BINDING);
        private final boolean depth = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        private final boolean depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        private final boolean alpha = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        private final boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        private final boolean cull = GL11.glIsEnabled(GL11.GL_CULL_FACE);
        private final boolean lighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
        private final boolean scissor = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);
        private final int[] textures = new int[4];
        private final boolean textured;
        private final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        private final FloatBuffer clear = BufferUtils.createFloatBuffer(16);
        private final ByteBuffer mask = BufferUtils.createByteBuffer(16);

        private PostState()
        {
            GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
            GL11.glGetFloat(GL11.GL_COLOR_CLEAR_VALUE, clear);
            GL11.glGetBoolean(GL11.GL_COLOR_WRITEMASK, mask);
            for (int i = 0; i < textures.length; i++)
            {
                GlStateManager.setActiveTexture(GL13.GL_TEXTURE0 + i);
                textures[i] = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
            }
            GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
            textured = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
            GlStateManager.setActiveTexture(active);
        }

        @Override public void close()
        {
            ARBShaderObjects.glUseProgramObjectARB(program);
            for (int i = 0; i < textures.length; i++)
            {
                GlStateManager.setActiveTexture(GL13.GL_TEXTURE0 + i);
                GlStateManager.bindTexture(textures[i]);
            }
            GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
            if (textured) GlStateManager.enableTexture2D(); else GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(active);
            if (depth) GlStateManager.enableDepth(); else GlStateManager.disableDepth();
            GlStateManager.depthMask(depthMask);
            if (alpha) GlStateManager.enableAlpha(); else GlStateManager.disableAlpha();
            if (blend) GlStateManager.enableBlend(); else GlStateManager.disableBlend();
            if (cull) GlStateManager.enableCull(); else GlStateManager.disableCull();
            if (lighting) GlStateManager.enableLighting(); else GlStateManager.disableLighting();
            if (scissor) GL11.glEnable(GL11.GL_SCISSOR_TEST); else GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.clearColor(clear.get(0), clear.get(1), clear.get(2), clear.get(3));
            GlStateManager.colorMask(mask.get(0) != 0, mask.get(1) != 0, mask.get(2) != 0, mask.get(3) != 0);
            GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, read);
            GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, draw);
            GlStateManager.viewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
        }
    }
}
