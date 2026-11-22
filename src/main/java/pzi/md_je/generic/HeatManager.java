package pzi.md_je.generic;

import net.minecraft.nbt.NBTTagCompound;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.entities.DD.StatsDD;

public class HeatManager 
{
    public float temp = StatsDD.targetCoreTemp;
    public float coreTemp = StatsDD.targetCoreTemp;
    public OverheatLevel overheat = OverheatLevel.None;
    public NBTTagCompound ToNBT() 
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setFloat("T", temp);
        tag.setFloat("cT", coreTemp);
        tag.setInteger("oh", overheat.ordinal());
        return tag;
    }   
    public void FromNBT(NBTTagCompound tag)
    {
        temp = tag.getFloat("T");
        coreTemp = tag.getFloat("cT");
        int oh = tag.getInteger("oh");
        if (oh > 0 && oh < OverheatLevel.values().length) 
        {
            overheat = OverheatLevel.values()[oh];
        }
    }
}
