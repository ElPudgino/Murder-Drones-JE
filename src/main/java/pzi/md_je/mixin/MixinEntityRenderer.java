package pzi.md_je.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import pzi.md_je.gui.PrettyGUI;
import pzi.md_je.rendering.BloomFramebuffer;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer
{
    // The first currentScreen read is inside the optional HUD condition. The
    // second is the unconditional screen check after world/HUD rendering, so
    // this hook also runs with F1 and composites before inventory/menu screens.
    @Inject(method = "updateCameraAndRender", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;",
            opcode = Opcodes.GETFIELD, ordinal = 1), require = 1, allow = 1)
    private void md_je$composeBloom(float partialTicks, long nanoTime, CallbackInfo ci)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.world == null || mc.player == null) return;
        if (!PrettyGUI.NeedGuiGlow()) return;
        PrettyGUI.PrepareDrawGUI();
        try
        {
            BloomFramebuffer.DrawAsBloom();
        }
        finally
        {
            PrettyGUI.EndDrawGUI();
        }
    }
}
