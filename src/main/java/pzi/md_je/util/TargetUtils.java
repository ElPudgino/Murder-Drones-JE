package pzi.md_je.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TargetUtils 
{
    public static boolean isCreative(Entity e)
    {
        if (e instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e;
            if (p.isCreative()) return true;
        }
        return false;
    }
    public static boolean isSpectator(Entity e)
    {
        if (e instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e;
            if (p.isSpectator()) return true;
        }
        return false;
    }
    public static boolean isCreativeOrSpec(Entity e)
    {
        return isCreative(e) || isSpectator(e);
    }
}
