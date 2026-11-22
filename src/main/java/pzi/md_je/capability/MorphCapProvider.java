package pzi.md_je.capability;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.Mod;
import pzi.md_je.MD;

@Mod.EventBusSubscriber
public class MorphCapProvider implements ICapabilitySerializable<NBTTagCompound>
{
    @CapabilityInject(IMorphCap.class)
    public static final Capability<IMorphCap> MORPH_CAP = null;

    private IMorphCap instance;
    public MorphCapProvider(EntityPlayer o)
    {
        MD.logger.info("morph cap provider constructed");
        instance = new MorphCap(o);
    }
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == MORPH_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == MORPH_CAP ? MORPH_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return (NBTTagCompound)MORPH_CAP.getStorage().writeNBT(MORPH_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        MORPH_CAP.getStorage().readNBT(MORPH_CAP, this.instance, null, nbt);
    }
}
