package pzi.md_je.particles;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EffTakeOff extends ParticleEffect
{
    public static int id;
    public Vec3d pos;
    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
    }
    @Override
    public void fromBytes(ByteBuf buf) 
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        pos = new Vec3d(x, y, z);
    }
    @Override
    public void Spawn(World world) 
    {
        
    }
    @Override
    public int GetID() 
    {   
        return id;
    }
       
}
