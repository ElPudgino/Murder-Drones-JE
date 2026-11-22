package pzi.md_je.rendering;

import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import pzi.md_je.entities.EntityMD;

public class EntityBloomTicket 
{
    public RenderLiving<EntityMD> render;
    public EntityMD entity;
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float partial;
    public EntityBloomTicket Set(RenderLiving<EntityMD> render1, EntityMD entity1,
        double x1, double y1, double z1, float yaw1, float partial1)
    {
        render = render1;
        entity = entity1;
        x = x1;
        y = y1;
        z = z1;
        yaw = yaw1;
        partial = partial1;
        return this;
    }
    public void doRender()
    {
        if (entity.deathTime > 0) return;
        GraphicsUtils.partial = partial;
        int hurttime = entity.hurtTime;
        entity.hurtTime = 0;
        render.doRender(entity, x, y, z, yaw, partial);
        entity.hurtTime = hurttime;
    }
}
