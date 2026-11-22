package pzi.md_je.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;

@Mod.EventBusSubscriber
public class EntityDeathEvent 
{
    @SubscribeEvent 
    public static void OnLivingDeath(LivingDeathEvent e)
    { 
        if (e.getEntityLiving() instanceof EntityPlayer || e.getEntityLiving() instanceof EntityVillager)
        {
            Entity sr = e.getSource().getImmediateSource();
            if (sr instanceof EntityPlayer)
            {
                IMorph sm = PlayerMorphs.GetMorph((EntityPlayer)sr);
                if (sm != null && sm.GetMorph() == Morphs.DD && e.getSource().damageType.equals("dd_claw"))
                {
                    ((DDMorph)sm).AddBlood(StatsDD.bloodPerKill);
                }
            }
        }

        if (e.getEntityLiving() instanceof EntityPlayer)
        {
            IMorph m = PlayerMorphs.GetMorph((EntityPlayer)e.getEntityLiving());
            if (m == null || m.GetMorph() == Morphs.None)
            {   
                
            }
            else if (m.GetMorph() == Morphs.DD)
            {
                if (((DDMorph)m).statsDD.OnDeath(e.getSource())) e.setCanceled(true);
            }
        }
        if (e.getEntityLiving() instanceof EntityDD)
        {
            EntityDD dd = (EntityDD)e.getEntityLiving();
            if (dd.getStats().OnDeath(e.getSource())) e.setCanceled(true);
        }
    }   
}
