package pzi.md_je.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.gui.DDGui;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.rendering.BloomFramebuffer;

@EventBusSubscriber (modid = MD.MODID, value = Side.CLIENT)
public class RenderOverlayEvent 
{
    @SideOnly (Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderOverlay(RenderGameOverlayEvent event) 
    {
        IMorph pc = PlayerMorphs.GetMorph(Minecraft.getMinecraft().player);
        if (pc == null) return;
        if (event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
            event.setCanceled(true);
            DDGui.instance.doDrawCrosshair = true;
        }   
        if (event.isCancelable() && (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH))
        {
            event.setCanceled(true);
        }
        if (event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
        {
            event.setCanceled(true);
        }
        if (event.isCancelable() && (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR
            || event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)
            || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)
        {
            if (pc.GetMorph() == Morphs.DD && ((DDMorph)pc).IsCombatMode()) event.setCanceled(true);
        }
    }
}
