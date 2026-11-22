package pzi.md_je.util;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import pzi.md_je.MD;

public class Collision 
{
    public static List<EntityLivingBase> RayCastEntities(World world ,Vec3d start, Vec3d dir, float maxdist, float width)
    {
        RayTraceResult res = world.rayTraceBlocks(start, start.add(dir.scale(maxdist)));
        Vec3d hit;
        if (res == null || res.typeOfHit == Type.MISS) hit = start.add(dir.scale(maxdist));
        else hit = res.hitVec;
        AxisAlignedBB aabb = new AxisAlignedBB(start, hit).grow(width);
        Predicate<EntityLivingBase> cond = (EntityLivingBase e) -> {
            return !e.isDead && e.deathTime == 0 && e.getHealth() > 0 && RayCastAABB(start, hit, e.getEntityBoundingBox().grow(width)).hit;
        };
        return world.getEntitiesWithinAABB(EntityLivingBase.class, aabb, cond);
    }   

    public static EntityLivingBase RayCastEntity(World world, Vec3d start, Vec3d dir, float maxdist, float width, Double dist, EntityLivingBase exclude)
    {
        RayTraceResult res = world.rayTraceBlocks(start, start.add(dir.scale(maxdist)));
        Vec3d hit;
        if (res == null || res.typeOfHit == Type.MISS) hit = start.add(dir.scale(maxdist));
        else hit = res.hitVec;
        AxisAlignedBB aabb = new AxisAlignedBB(start, hit).grow(width);
        Predicate<EntityLivingBase> cond;
        if (exclude == null)
        {
            cond = (EntityLivingBase e) -> {
                return !e.isDead && e.deathTime == 0 && e.getHealth() > 0 && RayCastAABB(start, hit, e.getEntityBoundingBox().grow(width)).hit;
            };
        }
        else
        {
            cond = (EntityLivingBase e) -> {
                return !e.isDead && e != exclude && e.deathTime == 0 && e.getHealth() > 0 && RayCastAABB(start, hit, e.getEntityBoundingBox().grow(width)).hit ;
            };
        }

        double bestT = 10e12f;
        EntityLivingBase bestE = null;
        AABBRaycastRes abres = null;
        for (EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, aabb, cond))
        {
            abres = RayCastAABB(start, hit, e.getEntityBoundingBox().grow(width));
            if (abres.dist < bestT)
            {
                bestE = e;
                bestT = abres.dist;
            }
        }
        dist = bestT;
        return bestE;
    }

    public static RayTraceResult RaycastDir(World world, Vec3d start, Vec3d dir, float maxdist)
    {
        return world.rayTraceBlocks(start, start.add(dir.scale(maxdist)));
    }

    public static AABBRaycastRes RayCastAABB(Vec3d start, Vec3d end, AxisAlignedBB aabb)
    {
        // Ray direction
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double dz = end.z - start.z;

        // Compute inverse direction (with infinity handling for zero components)
        double invDx = dx != 0.0 ? 1.0 / dx : Double.POSITIVE_INFINITY;
        double invDy = dy != 0.0 ? 1.0 / dy : Double.POSITIVE_INFINITY;
        double invDz = dz != 0.0 ? 1.0 / dz : Double.POSITIVE_INFINITY;

        // X slab
        double t1 = (aabb.minX - start.x) * invDx;
        double t2 = (aabb.maxX - start.x) * invDx;
        double tmin = Math.min(t1, t2);
        double tmax = Math.max(t1, t2);

        // Y slab
        t1 = (aabb.minY - start.y) * invDy;
        t2 = (aabb.maxY - start.y) * invDy;
        tmin = Math.max(tmin, Math.min(t1, t2));
        tmax = Math.min(tmax, Math.max(t1, t2));

        // Z slab
        t1 = (aabb.minZ - start.z) * invDz;
        t2 = (aabb.maxZ - start.z) * invDz;
        tmin = Math.max(tmin, Math.min(t1, t2));
        tmax = Math.min(tmax, Math.max(t1, t2));

        return new AABBRaycastRes(tmax >= tmin && tmax >= 0.0 && tmin <= 1.0, tmin);
    }

    public static EntityLivingBase RaycastEntityFromHead(EntityLivingBase o, float maxdist, float width, Double dist)
    {
        return RayCastEntity(o.world, math.getHeadPos(o), o.getLookVec(), maxdist, width, dist, o);
    }

}
