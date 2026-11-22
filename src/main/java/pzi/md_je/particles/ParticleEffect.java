package pzi.md_je.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public abstract class ParticleEffect 
{
    public static List<Supplier<ParticleEffect>> effects = new ArrayList<>();
    static 
    {
        effects.add(EffBulletHit::new);
        EffBulletHit.id = 0;
        effects.add(EffTakeOff::new);
        EffTakeOff.id = 1;
    }

    public abstract void fromBytes(ByteBuf buf);
    public abstract void Spawn(World world);
    public abstract void toBytes(ByteBuf buf);
    public abstract int GetID();
}
