package pzi.md_je.network.to_server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pzi.md_je.capability.IMorph;
import pzi.md_je.generic.IWeaponHolder;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CUseWeaponPacket;
import pzi.md_je.player.PlayerMorphs;

public class SUseWeaponPacket implements IMessage
{
    private int action;
    private int wepId;

    public SUseWeaponPacket() {}

    public SUseWeaponPacket(int a, int wep)
    {
        action = a;
        wepId = wep;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        action = buf.readInt();
        wepId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(action);
        buf.writeInt(wepId);
    }
    
    public static class Handler implements IMessageHandler<SUseWeaponPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SUseWeaponPacket message, MessageContext ctx) 
        {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            int a = message.action;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                HandleAction(serverPlayer, a, message.wepId);
            });
            return null;
        }
        private void HandleAction(EntityPlayer p, int ab, int wep)
        {
            IMorph m;
            if (p == null || (m = PlayerMorphs.GetMorph(p)) == null) return;
            if (m instanceof IWeaponHolder)
            {
                ((IWeaponHolder)m).OnWeaponAction(wep,ab, true);
                PacketHandler.INSTANCE.sendToAllTracking(new CUseWeaponPacket(p, ab, wep), p);
            }
        }
    }

}
