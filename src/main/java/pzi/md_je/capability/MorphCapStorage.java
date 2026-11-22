package pzi.md_je.capability;


import java.util.UUID;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import pzi.md_je.MD;
import pzi.md_je.player.Morphs;

public class MorphCapStorage implements IStorage<IMorphCap>
{
    @Override
    public NBTTagCompound writeNBT(Capability<IMorphCap> capability, IMorphCap instance, EnumFacing side)
    {
        NBTTagCompound compound = new NBTTagCompound();
        if (instance.GetMorph() == null)
        {
            compound.setString("morphID", "None");
            return compound;
        }
        compound.setString("morphID", instance.GetMorph().GetMorph().name());
        compound.setTag("m_data", instance.GetMorph().ToNBT());
        return compound;
    }
    @Override
    public void readNBT(Capability<IMorphCap> capability, IMorphCap instance, EnumFacing side, NBTBase nbt)
    {
        if (!(nbt instanceof NBTTagCompound))
        {
            instance.SetMorph(Morphs.None);
            return;
        }
        NBTTagCompound compound = (NBTTagCompound)nbt;
        Morphs morph;
        try
        {
            morph = Morphs.valueOf(compound.getString("morphID"));
        }
        catch (IllegalArgumentException ex)
        {
            morph = Morphs.None;
        }
        instance.SetMorph(morph);
        if (instance.GetMorph() != null)
        {
            instance.GetMorph().FromNBT(compound.getCompoundTag("m_data"));
        }
    }
}
