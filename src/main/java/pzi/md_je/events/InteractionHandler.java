package pzi.md_je.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.player.PlayerMorphs;

@Mod.EventBusSubscriber(modid = MD.MODID)
public final class InteractionHandler
{
    private static boolean cancelInteractions(EntityPlayer player)
    {
        IMorph morph = PlayerMorphs.GetMorph(player);
        return morph instanceof DDMorph && ((DDMorph)morph).IsCombatMode();
    }

    @SubscribeEvent
    public static void OnInteract(PlayerInteractEvent event)
    {
        if (event.isCancelable() && cancelInteractions(event.getEntityPlayer())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void OnUseItem(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer
                && cancelInteractions((EntityPlayer)event.getEntityLiving())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void OnBreak(BlockEvent.BreakEvent event)
    {
        if (cancelInteractions(event.getPlayer())) event.setCanceled(true);
    }


}
