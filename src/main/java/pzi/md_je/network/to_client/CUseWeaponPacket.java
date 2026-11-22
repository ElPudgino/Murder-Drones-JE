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
import pzi.md_je.generic.IWeaponHolder;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.TagUtil;

public class CUseWeaponPacket implements IMessage
{
    private int action;
    private int playerID;
    private int wepID;

    public CUseWeaponPacket() {}
    
    public CUseWeaponPacket(EntityPlayer pl, int a, int wep)
    {
        action = a;
        playerID = pl.getEntityId();
        wepID = wep;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        action = buf.readInt();
        playerID = buf.readInt();
        wepID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(action);
        buf.writeInt(playerID);
        buf.writeInt(wepID);
    }
    
    public static class Handler implements IMessageHandler<CUseWeaponPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CUseWeaponPacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();
            m.addScheduledTask(() -> {
                if (m.world == null) return;
                Entity p = m.world.getEntityByID(message.playerID);
                if (p instanceof EntityPlayer)
                {
                    HandleAction((EntityPlayer)p, message.action, message.wepID);
                }
            });
            
            return null;
        }
        private void HandleAction(EntityPlayer p, int ab, int wepid)
        {
            IMorph m;
            if (p == null || (m = PlayerMorphs.GetMorph(p)) == null) return;
            if (m instanceof IWeaponHolder) ((IWeaponHolder)m).OnWeaponAction(wepid, ab, false);
        }
    }
}
