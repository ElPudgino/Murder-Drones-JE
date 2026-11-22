package pzi.md_je.events;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

@Mod.EventBusSubscriber
public class PlayerCloneHandler 
{
    @SubscribeEvent 
    public static void OnPlayerClone(PlayerEvent.Clone e)
    {
        if (e.isWasDeath())
        {
            PlayerMorphs.CopyMorph(e.getOriginal(), e.getEntityPlayer());
            IMorph m = PlayerMorphs.GetMorph(e.getEntityPlayer());
            if (m != null) m.OnRespawned();
        }
    }
}
