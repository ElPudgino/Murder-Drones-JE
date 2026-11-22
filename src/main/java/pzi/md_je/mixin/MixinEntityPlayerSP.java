package pzi.md_je.mixin;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP
{
    @Shadow private int autoJumpTime;

    // Run after vanilla and Forge input handlers, but before vanilla consumes
    // the input for sprinting, jumping, creative flight, and movement this tick.
    @Inject(method = "onLivingUpdate", at = @At(value = "INVOKE",
            target = "Lnet/minecraftforge/client/ForgeHooksClient;onInputUpdate(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovementInput;)V",
            shift = At.Shift.AFTER, remap = false), require = 1)
    private void md_je$blockCriticalMovementInput(CallbackInfo ci)
    {
        EntityPlayerSP player = (EntityPlayerSP)(Object)this;
        IMorph morph = PlayerMorphs.GetMorph(player);
        if (!(morph instanceof DDMorph) || !((DDMorph)morph).statsDD.criticalState) return;

        MovementInput input = player.movementInput;
        input.moveForward = 0;
        input.moveStrafe = 0;
        input.forwardKeyDown = false;
        input.backKeyDown = false;
        input.leftKeyDown = false;
        input.rightKeyDown = false;
        input.jump = false;
        input.sneak = false;
        autoJumpTime = 0; // Vanilla would otherwise set jump again after this hook.
        player.setSprinting(false);
    }
}
