package pzi.md_je.network.to_client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.network.PacketData;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.TagUtil;

public class CMorphPacket implements IMessage
{
    private int morphID;
    private int playerID;
    private NBTTagCompound data;

    public CMorphPacket() {}
    
    public CMorphPacket(EntityPlayer pl, boolean full)
    {
        IMorph m = PlayerMorphs.GetMorph(pl);
        if (m == null) morphID = Morphs.None.ordinal();
        else 
        {
            morphID = m.GetMorph().ordinal();
            data = m.ToSyncNBT(full);
        }
        playerID = pl.getEntityId();

    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        morphID = PacketData.readMorph(buf);
        playerID = PacketData.readInt(buf);
        data = PacketData.readNBT(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(morphID);
        buf.writeInt(playerID);
        byte[] bytes;
        if (morphID == Morphs.None.ordinal())
        {
            buf.writeInt(0);
        }
        else
        {
            bytes = TagUtil.nbtToByteArray(data);
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
    }
    
    public static class Handler implements IMessageHandler<CMorphPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CMorphPacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();

            m.addScheduledTask(() -> {
                if (m.world == null) 
                {
                    MD.logger.warn("world was null on morph packet received");
                    return;
                }
                Entity entity = m.world.getEntityByID(message.playerID);
                if (!(entity instanceof EntityPlayer)) return;
                EntityPlayer pl = (EntityPlayer)entity;
                PlayerMorphs.SetMorph(pl, Morphs.values()[message.morphID]);
                if (PlayerMorphs.GetMorph(pl) != null && message.data != null)
                {
                    PlayerMorphs.GetMorph(pl).FromSyncNBT(message.data);
                }
            });
            
            return null;
        }
    }
}
