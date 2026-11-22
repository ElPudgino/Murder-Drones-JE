package pzi.md_je.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class math 
{
    public static float min(float a, float b)
    {
        return a < b ? a : b;
    }
    public static float max(float a, float b)
    {
        return a > b ? a : b;
    }
    public static final float DegToRad = 0.0174533f;
    public static Vec3d faceVec(EnumFacing dir)
    {
        if (dir == EnumFacing.EAST) return new Vec3d(1d, 0, 0);
        else if (dir == EnumFacing.WEST) return new Vec3d(-1d, 0, 0);
        else if (dir == EnumFacing.NORTH) return new Vec3d(0, 0, -1d);
        else if (dir == EnumFacing.SOUTH) return new Vec3d(0, 0, 1d);
        else if (dir == EnumFacing.UP) return new Vec3d(0, 1d, 0);
        else return new Vec3d(0, -1d, 0);
    }
    public static Vec3d getHeadPos(EntityLivingBase e)
    {
        return e.getPositionVector().add(0, e.getEyeHeight(), 0);
    }
}
