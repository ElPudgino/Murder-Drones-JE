package pzi.md_je.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.player.PlayerMorphs;

@EventBusSubscriber (modid = MD.MODID)
public class LogInHandler 
{
   
    @SubscribeEvent
    public static void OnStartTracking(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking event)
    {
        if (!event.getEntityPlayer().world.isRemote && event.getTarget() instanceof EntityPlayer)
            PacketHandler.INSTANCE.sendTo(new CMorphPacket((EntityPlayer)event.getTarget(), true),
                    (net.minecraft.entity.player.EntityPlayerMP)event.getEntityPlayer());
    }
}
