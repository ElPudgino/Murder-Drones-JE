package pzi.md_je.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.capability.IMorph;
import pzi.md_je.player.PlayerMorphs;

@Mod.EventBusSubscriber
public class HurtHandler 
{
    @SubscribeEvent 
    public static void OnLivingHurt(LivingHurtEvent e)
    {
        if (e.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e.getEntityLiving();
            IMorph m = PlayerMorphs.GetMorph(p);
            if (m != null)
            {
                m.OnHurt(e);
            }
        }
    }
    @SubscribeEvent 
    public static void OnLivingAttack(LivingAttackEvent e)
    {
        if (e.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e.getEntityLiving();
            IMorph m = PlayerMorphs.GetMorph(p);
            if (m != null)
            {
                m.OnAttacked(e);
            }
        }
    }
    @SubscribeEvent 
    public static void OnLivingDamage(LivingDamageEvent e)
    {
        if (e.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e.getEntityLiving();
            IMorph m = PlayerMorphs.GetMorph(p);
            if (m != null)
            {
                m.OnTookDamage(e);
            }
        }
    }
}
