package pzi.md_je.particles;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import pzi.md_je.MD;
import pzi.md_je.util.Vector3;

public class EffBulletHit extends ParticleEffect
{
    public static int id;
    protected Vec3d pos;
    protected Vector3 vel;
    protected int subType;
    protected int count;
    public EffBulletHit() {}
    public EffBulletHit(Vec3d p, Vec3d vl, int st, int c)
    {
        pos = p;
        vel = new Vector3(vl);
        subType = st;
        count = c;
    }
    @Override
    public void toBytes(ByteBuf buf) 
    {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
        buf.writeFloat(vel.x);
        buf.writeFloat(vel.y);
        buf.writeFloat(vel.z);
        buf.writeInt(subType);
        buf.writeInt(count);
    }
    @Override
    public void fromBytes(ByteBuf buf) 
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        pos = new Vec3d(x, y, z);
        vel = new Vector3();
        vel.x = buf.readFloat();
        vel.y = buf.readFloat();
        vel.z = buf.readFloat();
        subType = buf.readInt();
        count = buf.readInt();
    }

    @Override
    public void Spawn(World world) 
    {
        Vec3d vel_final;
        for (int i = 0; i < count; i++)
        {
            vel_final = vel.toVec3d().normalize().add(MD.random.nextFloat()*1.4f-0.7f, MD.random.nextFloat()*1.4f-0.7f, MD.random.nextFloat()*1.4f-0.7f).scale(vel.length());
            Particle p = new HitDebris(world, pos.x, pos.y, pos.z, vel_final.x, vel_final.y, vel_final.z, subType);
            ParticleUtils.spawnParticle(world, p);
        }
    }
    @Override  
    public int GetID()
    {
        return id;
    }
}
