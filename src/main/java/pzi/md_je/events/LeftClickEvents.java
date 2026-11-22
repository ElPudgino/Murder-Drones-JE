package pzi.md_je.events;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

@EventBusSubscriber (modid = MD.MODID)
public class LeftClickEvents 
{
    @SubscribeEvent
    public static void AttackEntity(AttackEntityEvent event) 
    {
        IMorph pc = PlayerMorphs.GetMorph(event.getEntityPlayer());
        if (pc == null) return;
        if (pc.OnAttackEntity(event)) event.setCanceled(true);
    }
    @SubscribeEvent
    public static void LeftClickBlock(PlayerInteractEvent.LeftClickBlock event) 
    {
        IMorph pc = PlayerMorphs.GetMorph(event.getEntityPlayer());
        if (pc == null) return;
        if (pc.OnLeftClickBlock(event)) event.setCanceled(true);
    }
    @SubscribeEvent
    public static void LeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) 
    {
        IMorph pc = PlayerMorphs.GetMorph(event.getEntityPlayer());
        if (pc == null) return;
        pc.OnLeftClickEmpty(event);
    }
}
