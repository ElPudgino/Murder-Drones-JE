package pzi.md_je.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class DamageUtils 
{
    public static void DamageNoIFrames(EntityLivingBase e, DamageSource d, float amount)
    {
        int hrt = e.hurtResistantTime;
        e.hurtResistantTime = 0;
        e.attackEntityFrom(d, amount);
        e.hurtResistantTime = hrt;
    }

}
