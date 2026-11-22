package pzi.md_je.events;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.entities.DD.StatsDD.AbilityType;
import pzi.md_je.generic.MouseHandler;
import pzi.md_je.loading.ClientProxy;
import pzi.md_je.player.PlayerMorphs;


@EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public class KeybindsHandler 
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public static void onEvent(KeyInputEvent event)
    {
        if (ClientProxy.keyFly.isPressed()) // start flying
        {
            PlayerMorphs.TryUseAbility(AbilityType.Fly, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyStopFly.isPressed()) // stop flying
        {
            PlayerMorphs.TryUseAbility(AbilityType.FlyStop, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyUnfoldWings.isPressed()) 
        {
            PlayerMorphs.TryUseAbility(AbilityType.UnfoldWings, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyFoldWings.isPressed())
        {
            PlayerMorphs.TryUseAbility(AbilityType.FoldWings, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyTakeOff.isPressed())
        {
            PlayerMorphs.TryUseAbility(AbilityType.TakeOff, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyCombatMode.isPressed())
        {
            PlayerMorphs.TryUseAbility(AbilityType.CombatToggle, Minecraft.getMinecraft().player);
        }
        if (ClientProxy.keyNextWeapon.isPressed())
        {
            PlayerMorphs.TryUseAbility(AbilityType.WeaponSwap, Minecraft.getMinecraft().player);
        }
    }
}
