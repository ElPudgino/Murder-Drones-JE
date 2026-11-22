package pzi.md_je.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.IMorphCap;
import pzi.md_je.capability.MorphCapProvider;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.RenderEntityDD;
import pzi.md_je.loading.EntityRenderHandler;
import pzi.md_je.player.PlayerMorphs;

@EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public class RenderPlayerEventHandler 
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void OnRenderPlayer(RenderPlayerEvent.Pre e)
    {
        IMorph morph = PlayerMorphs.GetMorph(e.getEntityPlayer());
        if (morph == null) return;
        morph.RenderAt(e.getX(), e.getY(), e.getZ(), e.getPartialRenderTick());
        e.setCanceled(true);
    }
}
