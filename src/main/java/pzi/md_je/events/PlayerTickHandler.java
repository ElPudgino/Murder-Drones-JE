package pzi.md_je.events;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

public class PlayerTickHandler
{
    @SubscribeEvent 
    public void OnPlayerTick(PlayerTickEvent e)
    {
        if (e.phase != net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) return;
        IMorph m = PlayerMorphs.GetMorph(e.player);
        if (m == null) return;
        m.OnUpdate(e.side);
    }
}
