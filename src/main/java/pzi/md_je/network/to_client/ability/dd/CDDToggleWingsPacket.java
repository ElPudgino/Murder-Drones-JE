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

public class CDDToggleWingsPacket implements IMessage
{
    private int ddID; // can both npc or player
    private boolean stateWings;
    private boolean stateFly;

    public CDDToggleWingsPacket() {}
    
    public CDDToggleWingsPacket(EntityLivingBase dd, boolean wings, boolean fly)
    {
        ddID = dd.getEntityId();
        stateWings = wings;
        stateFly = fly;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        ddID = buf.readInt();
        stateWings = buf.readBoolean();
        stateFly = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(ddID);
        buf.writeBoolean(stateWings);
        buf.writeBoolean(stateFly);
    }
    
    public static class Handler implements IMessageHandler<CDDToggleWingsPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CDDToggleWingsPacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();
            m.addScheduledTask(() -> {
                HandleAction(message);
            });
            
            return null;
        }
        private void HandleAction(CDDToggleWingsPacket msg)
        {
            Minecraft mc = Minecraft.getMinecraft();
            Entity dd = mc.world.getEntityByID(msg.ddID);
            if (dd == null) return;
            if (dd instanceof EntityPlayer)
            {
                IMorph m = PlayerMorphs.GetMorph((EntityPlayer)dd);
                if (m == null || m.GetMorph() != Morphs.DD) return;
                FlightDD f = ((DDMorph)m).flightController;
                f.SetFlyState(msg.stateFly);
                f.SetWingState(msg.stateWings);
            }
            else if (dd instanceof EntityDD)
            {
                // Add when start implementing the mob
            }
        }
    }
}
