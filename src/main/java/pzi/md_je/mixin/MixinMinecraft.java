package pzi.md_je.mixin;

import net.minecraft.client.Minecraft;
import pzi.md_je.events.InputHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Keep vanilla input intact in default mode; combat owns both mouse buttons. */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
    @Inject(method = {"clickMouse", "rightClickMouse"}, at = @At("HEAD"), cancellable = true)
    private void md_je$combatClick(CallbackInfo ci)
    {
        if (InputHandler.InterceptInput()) ci.cancel();
    }

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"), cancellable = true)
    private void md_je$combatMining(boolean leftClick, CallbackInfo ci)
    {
        if (InputHandler.InterceptInput())
        {
            Minecraft.getMinecraft().playerController.resetBlockRemoving();
            ci.cancel();
        }
    }
}
