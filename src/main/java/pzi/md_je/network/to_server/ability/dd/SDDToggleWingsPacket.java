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
import pzi.md_je.entities.DD.FlightDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.ability.dd.CDDFeedPacket;
import pzi.md_je.network.to_client.ability.dd.CDDToggleWingsPacket;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;

public class SDDToggleWingsPacket implements IMessage
{
    private boolean stateWings;
    private boolean stateFly;

    public SDDToggleWingsPacket() {}

    public SDDToggleWingsPacket(boolean wings, boolean fly)
    {
        stateFly = fly;
        stateWings = wings;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        stateWings = buf.readBoolean();
        stateFly = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeBoolean(stateWings);
        buf.writeBoolean(stateFly);
    }
    
    public static class Handler implements IMessageHandler<SDDToggleWingsPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SDDToggleWingsPacket message, MessageContext ctx) 
        {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                HandleAction(serverPlayer, message);
            });
            return null;
        }
        private void HandleAction(EntityPlayer p, SDDToggleWingsPacket msg)
        {
            IMorph m;
            if (p == null || (m = PlayerMorphs.GetMorph(p)) == null) return;
            if (m.GetMorph() == Morphs.DD)
            {
                FlightDD f = ((DDMorph)m).flightController; 
                f.TrySetWingState(msg.stateWings);
                f.TrySetFlyState(msg.stateFly);
                PacketHandler.INSTANCE.sendToAllTracking(new CDDToggleWingsPacket(p, f.areWingsOut(), f.isFlying()), p);
            }
        }
    }
}
