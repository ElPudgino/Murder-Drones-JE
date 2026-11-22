package pzi.md_je.anim;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pzi.md_je.MD;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.util.math;

public class Transforms 
{
    public static Quaternion GetGlobalRotation(ChModelRenderer part, float entityYaw)
    {
        Quaternion es = GetRotationRecursive(part);
        Quaternion ent = FromEulerXYZ(0, entityYaw, 0);
        Quaternion.mul(ent, es, es);
        return es;
    }   
    private static Quaternion GetRotationRecursive(ChModelRenderer part)
    {
        if (part == null) return new Quaternion(0,0,0,1);
        Quaternion res = FromEulerXYZ(part.rotateAngleX, part.rotateAngleY, part.rotateAngleZ);
        if (part.parent != null)
        {
            Quaternion q = GetRotationRecursive(part.parent);
            Quaternion.mul(q, res, res);
        }
        return res;
    }
    public static float SimplifyAngle(float a)
    {
        if (a > 180f) return a - MathHelper.floor((a + 180f) / 360f) * 360f;
        if (a < -180f) return a + MathHelper.floor((-a + 180f) / 360f) * 360f;
        return a;
    }
    public static Quaternion FromEulerXYZ(float x, float y, float z)
    {
        // Assumes x, y, z are in radians.
        // Rotation order: X then Y then Z, i.e. q = qZ * qY * qX.

        float hx = x * 0.5f;
        float hy = y * 0.5f;
        float hz = z * 0.5f;

        float sx = (float)Math.sin(hx);
        float cx = (float)Math.cos(hx);
        float sy = (float)Math.sin(hy);
        float cy = (float)Math.cos(hy);
        float sz = (float)Math.sin(hz);
        float cz = (float)Math.cos(hz);

        float qx = sx * cy * cz - cx * sy * sz;
        float qy = cx * sy * cz + sx * cy * sz;
        float qz = cx * cy * sz - sx * sy * cz;
        float qw = cx * cy * cz + sx * sy * sz;

        return new Quaternion(qx, qy, qz, qw);
    }
    public static Quaternion FromEulerYXZ(float x, float y, float z)
    {
        // Assumes x, y, z are in radians.
        // Rotation order: Y then X then Z, i.e. q = qZ * qX * qY.

        float hx = x * 0.5f;
        float hy = y * 0.5f;
        float hz = z * 0.5f;

        float sx = (float)Math.sin(hx);
        float cx = (float)Math.cos(hx);
        float sy = (float)Math.sin(hy);
        float cy = (float)Math.cos(hy);
        float sz = (float)Math.sin(hz);
        float cz = (float)Math.cos(hz);

        float qx = sx * cy * cz - cx * sy * sz;
        float qy = cx * sy * cz + sx * cy * sz;
        float qz = sx * sy * cz + cx * cy * sz;
        float qw = cx * cy * cz - sx * sy * sz;

        return new Quaternion(qx, qy, qz, qw);
    }
    public static void SetGlobalRotation(ChModelRenderer part, Quaternion q, float entityYaw)
    {
        Quaternion res = new Quaternion();
        Quaternion q0 = GetGlobalRotation(part.parent, entityYaw);
        Quaternion.negate(q0, q0);
        Quaternion.mul(q0, q, res);
        SetFromQuaternion(part, res);
    }
    public static void SetFromQuaternion(ChModelRenderer part, Quaternion q)
    {
        float qx = q.x;
        float qy = q.y;
        float qz = q.z;
        float qw = q.w;

        // Compute sin(y)
        float sy = 2.0f * (qw * qy - qx * qz);
        // Clamp to avoid NaN from asin
        if (sy > 1.0f) sy = 1.0f;
        if (sy < -1.0f) sy = -1.0f;

        float y = (float)Math.asin(sy);

        float x, z;
        if (Math.abs(sy) > 0.99999f)
        {
            // Gimbal lock: y is ±pi/2
            // Set x = 0 and compute z
            float R01 = 2.0f * (qx * qy - qz * qw);
            float R11 = 1.0f - 2.0f * (qx * qx + qz * qz);
            if (sy > 0.0f) // y = +pi/2
            {
                x = 0.0f;
                z = -(float)Math.atan2(R01, R11);
            }
            else // y = -pi/2
            {
                x = 0.0f;
                z = (float)Math.atan2(-R01, R11);
            }
        }
        else
        {
            // Normal case
            float R21 = 2.0f * (qy * qz + qw * qx);
            float R22 = 1.0f - 2.0f * (qx * qx + qy * qy);
            x = (float)Math.atan2(R21, R22);

            float R10 = 2.0f * (qx * qy + qz * qw);
            float R00 = 1.0f - 2.0f * (qy * qy + qz * qz);
            z = (float)Math.atan2(R10, R00);
        }

        part.rotateAngleX = x;
        part.rotateAngleY = y;
        part.rotateAngleZ = z;
    }

    public static void AimPartAt(Vec3d trg, EntityDD self, ChModelRenderer part)
	{
		float dt = GraphicsUtils.GetPartialTick();
		double x = trg.x - self.prevPosX + (self.prevPosX - self.posX) * dt;
		double y = trg.y - self.prevPosY + (self.prevPosY - self.posY) * dt;
		double z = trg.z - self.prevPosZ + (self.prevPosZ - self.posZ) * dt;
		AimPartDir(new Vec3d(x, y, z), self, part);
	}
	public static void AimPartDir(Vec3d dir, EntityDD self, ChModelRenderer part)
	{
		float eyaw = self.prevRenderYawOffset + (self.renderYawOffset - self.prevRenderYawOffset) * GraphicsUtils.GetPartialTick();
		
		double dx = dir.x;
		double dy = dir.y;
		double dz = dir.z;

		float yaw = (float) Math.atan2(dz, dx) - (float) (Math.PI / 2);
		double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
		float pitch = (float) (3*Math.PI / 2) - (float) Math.atan2(dy, horizontalDistance);

		Quaternion q = Transforms.FromEulerXYZ(pitch, yaw, 0);
		Transforms.SetGlobalRotation(part, q, eyaw * math.DegToRad);
	}
}
