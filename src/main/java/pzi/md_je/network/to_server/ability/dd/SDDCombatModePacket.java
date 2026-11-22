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
import pzi.md_je.network.to_client.ability.dd.CDDCombatModePacket;
import pzi.md_je.network.to_client.ability.dd.CDDFeedPacket;
import pzi.md_je.network.to_client.ability.dd.CDDToggleWingsPacket;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;

public class SDDCombatModePacket implements IMessage
{
    private boolean state;

    public SDDCombatModePacket() {}

    public SDDCombatModePacket(boolean c)
    {
        state = c;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        state = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeBoolean(state);
    }
    
    public static class Handler implements IMessageHandler<SDDCombatModePacket, IMessage>
    {
        @Override
        public IMessage onMessage(SDDCombatModePacket message, MessageContext ctx) 
        {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                HandleAction(serverPlayer, message);
            });
            return null;
        }
        private void HandleAction(EntityPlayer p, SDDCombatModePacket msg)
        {
            IMorph m;
            if (p == null || (m = PlayerMorphs.GetMorph(p)) == null) return;
            if (m.GetMorph() == Morphs.DD)
            {
                ((DDMorph)m).SetCombatMode(msg.state); 
                PacketHandler.INSTANCE.sendToAllTracking(new CDDCombatModePacket(p, msg.state), p);
            }
        }
    }
}
