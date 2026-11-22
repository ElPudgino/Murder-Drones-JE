package pzi.md_je.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.generic.MouseHandler;
import pzi.md_je.loading.ClientProxy;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.player.PlayerMorphs;

@Mod.EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public final class InputHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onMouseEvent(MouseEvent event) 
    {
        EntityPlayerSP ply = Minecraft.getMinecraft().player;
        
        if (event.getButton() != -1 && ply != null && Minecraft.getMinecraft().inGameHasFocus) {

            int keyCode = event.getButton() - 100;

            if ((keyCode == Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() ||
                keyCode == Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode()))
            {
                MouseHandler.Update(keyCode, event.isButtonstate());
                if (InterceptInput()) event.setCanceled(true);
                
                KeyBinding.setKeyBindState(keyCode, false);
            }
        }
    }

    public static boolean InterceptInput()
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return false;
        IMorph morph = PlayerMorphs.GetMorph(mc.player);
        if (morph == null) return false;
        return (morph instanceof DDMorph && (((DDMorph)morph).IsCombatMode()) || ((DDMorph)morph).statsDD.criticalState) || ClientProxy.keySpecial.isKeyDown();
    }

}
