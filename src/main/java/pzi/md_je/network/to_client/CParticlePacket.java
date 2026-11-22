package pzi.md_je.network.to_client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.particles.ParticleEffect;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.TagUtil;

public class CParticlePacket implements IMessage
{
    private ParticleEffect particleEffect;

    public CParticlePacket() {}
    
    public CParticlePacket(ParticleEffect effect)
    {
        particleEffect = effect;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
        int id = buf.readInt();
        if (ParticleEffect.effects.size() <= id) return;
        particleEffect = ParticleEffect.effects.get(id).get();
        particleEffect.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeInt(particleEffect.GetID());
        particleEffect.toBytes(buf);
    }
    
    public static class Handler implements IMessageHandler<CParticlePacket, IMessage>
    {
        @Override
        public IMessage onMessage(CParticlePacket message, MessageContext ctx) 
        {
            Minecraft m = Minecraft.getMinecraft();

            m.addScheduledTask(() -> {
                if (m.world == null) 
                {
                    MD.logger.warn("world was null on particle packet received");
                    return;
                }
                message.particleEffect.Spawn(m.world);
            });
            
            return null;
        }
    }
}
