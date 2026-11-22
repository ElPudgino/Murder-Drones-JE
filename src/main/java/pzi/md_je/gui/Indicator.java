package pzi.md_je.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.loading.ClientProxy;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.rendering.BloomFramebuffer;
import pzi.md_je.rendering.BloomQueue;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public class Indicator
{
    @SubscribeEvent
    public static void onRenderPost(final RenderWorldLastEvent event)
    {
        BloomQueue.renderedWorld = true;  
        if (BloomQueue.tickets.isEmpty()) return;
        BloomQueue.RenderBloomPass();   
        PrettyGUI.PrepareDrawGUI();
        BloomFramebuffer.DrawAsBloom();
        PrettyGUI.EndDrawGUI();
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGuiPost(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        EntityPlayer pl = Minecraft.getMinecraft().player;
        IMorph pc = PlayerMorphs.GetMorph(pl);
        PrettyGUI.PrepareDrawGUI();
        if (pc != null && !event.isCanceled())
        {
            pc.GetGui().Render();
        }    
        PrettyGUI.EndDrawGUI(); 
    }

    @SubscribeEvent 
    public static void onRenderTickEnd(TickEvent.RenderTickEvent event)
    {
        BloomQueue.renderedWorld = false;
    }
}
