package pzi.md_je.network.to_server.ability.dd;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.ability.dd.CDDFeedPacket;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;

public class SDDFeedPacket implements IMessage
{
    private int targetID;

    public SDDFeedPacket() {}

    public SDDFeedPacket(EntityLivingBase e)
    {
        targetID = e.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        targetID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(targetID);
    }
    
    public static class Handler implements IMessageHandler<SDDFeedPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SDDFeedPacket message, MessageContext ctx) 
        {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                HandleAction(serverPlayer, message);
            });
            return null;
        }
        private void HandleAction(EntityPlayer p, SDDFeedPacket msg)
        {
            IMorph m;
            if (p == null || (m = PlayerMorphs.GetMorph(p)) == null) return;
            if (m.GetMorph() == Morphs.DD)
            {
                Entity e = p.world.getEntityByID(msg.targetID);
                if (e instanceof EntityLivingBase && StatsDD.isValidFeedTarget((EntityLivingBase)e) 
                    && p.getEntityBoundingBox().grow(0.8f).intersects(e.getEntityBoundingBox()))
                {
                    ((DDMorph)m).Feed((EntityLivingBase)e);
                    PacketHandler.INSTANCE.sendToAllTracking(new CDDFeedPacket(p, (EntityLivingBase)e), e);
                }
            }
        }
    }
}
