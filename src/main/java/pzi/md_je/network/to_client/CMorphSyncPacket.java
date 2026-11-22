package pzi.md_je.network.to_client;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.network.PacketData;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.TagUtil;

public class CMorphSyncPacket implements IMessage
{
    private int[] morphIDs;
    private NBTTagCompound[] datas;
    private int[] playerIDs;

    public CMorphSyncPacket() {}
    
    public CMorphSyncPacket(List<EntityPlayerMP> pl)
    {
        morphIDs = new int[pl.size()];
        playerIDs = new int[pl.size()];
        datas = new NBTTagCompound[pl.size()];
        for (int i = 0; i < pl.size(); i++)
        {
            IMorph m = PlayerMorphs.GetMorph(pl.get(i));
            if (m == null)
            {
                morphIDs[i] = Morphs.None.ordinal();
                datas[i] = null;
            }
            else
            {
                datas[i] = m.ToSyncNBT(true);
                morphIDs[i] = m.GetMorph().ordinal();
            }
            
            playerIDs[i] = pl.get(i).getEntityId();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        int count = PacketData.readInt(buf);
        if (count < 0 || count > PacketData.MAX_PLAYERS || count > buf.readableBytes() / 12)
            throw new io.netty.handler.codec.DecoderException("Invalid morph sync count: " + count);
        morphIDs = new int[count];
        playerIDs = new int[count];
        datas = new NBTTagCompound[count];
        for (int i = 0; i < count; i++)
        {
            playerIDs[i] = PacketData.readInt(buf);
            morphIDs[i] = PacketData.readMorph(buf);
            datas[i] = PacketData.readNBT(buf);
        }   
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(morphIDs.length);
        byte[] data;
        for (int i = 0; i < morphIDs.length; i++)
        {
            buf.writeInt(playerIDs[i]);
            buf.writeInt(morphIDs[i]);
            if (morphIDs[i] == Morphs.None.ordinal())
            {
                buf.writeInt(0);
            }
            else
            {
                data = TagUtil.nbtToByteArray(datas[i]);
                buf.writeInt(data.length);
                buf.writeBytes(data);
            }
        }
        
    }
    
    public static class Handler implements IMessageHandler<CMorphSyncPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CMorphSyncPacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();
           
            m.addScheduledTask(() -> {
                for (int i = 0; i < message.morphIDs.length; i++)
                {
                    World w = m.world;
                    if (w == null)
                    {   
                        MD.logger.warn("world was null on morph sync packet received"); 
                        continue;
                    }
                    Entity entity = w.getEntityByID(message.playerIDs[i]);
                    if (!(entity instanceof EntityPlayer)) continue;
                    EntityPlayer pl = (EntityPlayer)entity;
                    PlayerMorphs.SetMorph(pl, Morphs.values()[message.morphIDs[i]]);
                    if (PlayerMorphs.GetMorph(pl) != null && message.datas[i] != null)
                    {
                        PlayerMorphs.GetMorph(pl).FromSyncNBT(message.datas[i]);
                    }                
                }
            });
            
            return null;
        }
    }
}
