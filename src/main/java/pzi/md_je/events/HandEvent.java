package pzi.md_je.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

@EventBusSubscriber (modid = MD.MODID, value = Side.CLIENT)
public class HandEvent 
{


    @SideOnly (Side.CLIENT)
    @SubscribeEvent
    public static void OnRenderHand(RenderSpecificHandEvent event)
    {
        IMorph p = PlayerMorphs.GetMorph(Minecraft.getMinecraft().player);
        if (p == null) return;
        if (event.getHand() == EnumHand.OFF_HAND || (!event.getItemStack().isEmpty() && !p.HideHeldItem(EnumHand.MAIN_HAND))) return;
        p.RenderHands(event.getSwingProgress());
        event.setCanceled(true);
    }
}
