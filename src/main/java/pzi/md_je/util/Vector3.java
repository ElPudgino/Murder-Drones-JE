package pzi.md_je.util;

import net.minecraft.util.math.Vec3d;

public class Vector3 
{
    public float x;
    public float y;
    public float z;

    public Vector3(){}
    public Vector3(float xa, float ya, float za)
    {
        x = xa; y = ya; z = za;
    }

    public static Vector3 add(Vector3 a, Vector3 b)
    {
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public float length()
    {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public static Vector3 zero()
    {
        return new Vector3(0, 0, 0);
    }

    public void normalize()
    {
        float length = this.length();
        if (length < 0.00001f) {x = 1; y = 0; z = 0;}
        else {x = x/length; y = y/length; z = z/length;}
    }

    public Vector3(Vec3d vecd)
    {
        x = (float)vecd.x;
        y = (float)vecd.y;
        z = (float)vecd.z;
    }

    public Vec3d toVec3d()
    {
        return new Vec3d(x, y, z);
    }
}
