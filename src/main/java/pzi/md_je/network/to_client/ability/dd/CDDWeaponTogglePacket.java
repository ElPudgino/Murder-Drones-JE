package pzi.md_je.network.to_client.ability.dd;

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
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.FlightDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.generic.IWeaponHolder;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.TagUtil;

public class CDDWeaponTogglePacket implements IMessage
{
    private int ddID; // can both npc or player
    private int wepID;

    public CDDWeaponTogglePacket() {}
    
    public CDDWeaponTogglePacket(EntityLivingBase dd, int wep)
    {
        ddID = dd.getEntityId();
        wepID = wep;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        ddID = buf.readInt();
        wepID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(ddID);
        buf.writeInt(wepID);
    }
    
    public static class Handler implements IMessageHandler<CDDWeaponTogglePacket, IMessage>
    {
        @Override
        public IMessage onMessage(CDDWeaponTogglePacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();
            m.addScheduledTask(() -> {
                HandleAction(message);
            });
            
            return null;
        }
        private void HandleAction(CDDWeaponTogglePacket msg)
        {
            Minecraft mc = Minecraft.getMinecraft();
            Entity dd = mc.world.getEntityByID(msg.ddID);
            if (dd == null) return;
            if (dd instanceof EntityPlayer)
            {
                IMorph m = PlayerMorphs.GetMorph((EntityPlayer)dd);
                if (m == null || m.GetMorph() != Morphs.DD) return;
                ((DDMorph)m).SelectWeapon(msg.wepID);
            }
            else if (dd instanceof EntityDD)
            {
                // Add when start implementing the mob
            }
        }
    }
}
