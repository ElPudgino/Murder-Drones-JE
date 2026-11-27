package pzi.md_je.anim;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import pzi.md_je.MD;
import pzi.md_je.entities.EntityMD;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.rendering.BloomQueue;
import pzi.md_je.rendering.AnimLoader.AnimationData;
import pzi.md_je.rendering.AnimLoader.BoneAnimation;
import pzi.md_je.rendering.AnimLoader.Interpolation;
import pzi.md_je.rendering.AnimLoader.Keyframe;
import pzi.md_je.util.Vector3;
import pzi.md_je.util.math;

public class AnimModelBase extends ModelBase
{
    List<ChModelRenderer> bones = new ArrayList<ChModelRenderer>();

    public void AnimFrame(EntityAnim eanim)
    {
        AnimationData anim = eanim.curData;
        for (ChModelRenderer bone : bones)
        {
            if (!anim.bones.containsKey(bone.boxName)) continue;
            BoneAnimation banim = anim.bones.get(bone.boxName);
            if (banim.positionKeyframes.size() > 0)
            {
                Keyframe pos = GetKeyframe(banim.positionKeyframes, 0, eanim.curTime, anim.loop);
                Vector3 posoffset;
                if (pos.interpolation == Interpolation.LINEAR) posoffset = Lerp(banim.positionKeyframes, eanim.curTime, anim.loop);
                else posoffset = CatmullRom(banim.positionKeyframes, eanim.curTime, anim.loop);
                bone.offsetX += (posoffset.x / 16f) * eanim.weight;
                bone.offsetY += (posoffset.y / 16f) * eanim.weight;
                bone.offsetZ += (posoffset.z / 16f) * eanim.weight;
            }
            if (banim.rotationKeyframes.size() > 0)
            {
                Keyframe rot = GetKeyframe(banim.rotationKeyframes, 0, eanim.curTime, anim.loop);
                Vector3 rotoffset;
                if (rot.interpolation == Interpolation.LINEAR) rotoffset = Lerp(banim.rotationKeyframes, eanim.curTime, anim.loop);
                else rotoffset = CatmullRom(banim.rotationKeyframes, eanim.curTime, anim.loop);
                bone.rotateAngleX += rotoffset.x * math.DegToRad * eanim.weight;
                bone.rotateAngleY += rotoffset.y * math.DegToRad * eanim.weight;
                bone.rotateAngleZ += rotoffset.z * math.DegToRad * eanim.weight;
            }
            if (banim.scaleKeyframes.size() > 0)
            {
                Keyframe sc = GetKeyframe(banim.scaleKeyframes, 0, eanim.curTime, anim.loop);
                Vector3 scoffset;
                if (sc.interpolation == Interpolation.LINEAR) scoffset = Lerp(banim.scaleKeyframes, eanim.curTime, anim.loop);
                else scoffset = CatmullRom(banim.scaleKeyframes, eanim.curTime, anim.loop);
                bone.scale = (float)Math.pow(scoffset.x * scoffset.y * scoffset.z, 1/3d);
            }
        }
    }

    public void ResetTransforms()
    {
        for (ChModelRenderer bone : bones)
        {
            bone.rotateAngleX = bone.baseRotAngleX;
            bone.rotateAngleY = bone.baseRotAngleY;
            bone.rotateAngleZ = bone.baseRotAngleZ;
            bone.offsetX = 0f;
            bone.offsetY = 0f;
            bone.offsetZ = 0f;
        }
    }

    protected Keyframe GetKeyframe(List<Keyframe> frames, int offset, float time, boolean loop)
    {
        int ind = 0;
        for (int i = 0; i < frames.size(); i++)
        {
            if (frames.get(i).time <= time) 
            {
                ind = i;
            }
            else break;
        }
        ind = ind + offset;
        if (!loop)
        {
            if (ind < 0) ind = 0;
            if (ind >= frames.size()) ind = frames.size() - 1;
        }
        else
        {
            ind = ind % frames.size();
            if (ind < 0) ind += frames.size();
        }
        return frames.get(ind);
    }
    protected Vector3 Lerp(List<Keyframe> frames, float time, boolean loop)
    {
        Keyframe fr1 = GetKeyframe(frames, 0, time, loop);
        Keyframe fr2 = GetKeyframe(frames, 1, time, loop);
        if (fr1 == fr2) return new Vector3(fr1.vector[0], fr1.vector[1], fr1.vector[2]);
        float dt = fr2.time - fr1.time;
        float val = (time - fr1.time) / dt;
        return new Vector3(fr1.vector[0] * (1f - val) + fr2.vector[0] * val,
         fr1.vector[1] * (1f - val) + fr2.vector[1] * val,
         fr1.vector[2] * (1f - val) + fr2.vector[2] * val);
    }
    protected Vector3 CatmullRom(List<Keyframe> frames, float time, boolean loop)
    {
        Keyframe fr1 = GetKeyframe(frames, -1, time, loop);
        Keyframe fr2 = GetKeyframe(frames, 0, time, loop);
        Keyframe fr3 = GetKeyframe(frames, 1, time, loop);
        Keyframe fr4 = GetKeyframe(frames, 2, time, loop);
        if (fr2 == fr3) return new Vector3(fr2.vector[0], fr2.vector[1], fr2.vector[2]);
        float dt = fr3.time - fr2.time;
        float val = (time - fr2.time) / dt;
        float[] res = new float[3];
        float m2, m3;
        for (int i = 0; i < 3; i++)
        {
            m2 = (fr3.vector[i] - fr1.vector[i])/2f;
            m3 = (fr4.vector[i] - fr2.vector[i])/2f;
            res[i] = (2*val*val*val - 3*val*val + 1) * fr2.vector[i] +
                     (val*val*val - 2*val*val + val) * m2 +
                     (-2*val*val*val + 3*val*val) * fr3.vector[i] +
                     (val*val*val - val*val) * m3;
        }
        return new Vector3(res[0], res[1], res[2]);
    }

    public static void setRotationAngle(ChModelRenderer ChModelRenderer, float x, float y, float z) 
	{
		ChModelRenderer.rotateAngleX = x;
		ChModelRenderer.rotateAngleY = y;
		ChModelRenderer.rotateAngleZ = z;
		ChModelRenderer.baseRotAngleX = x;
		ChModelRenderer.baseRotAngleY = y;
		ChModelRenderer.baseRotAngleZ = z;
	}
    protected void ApplyAnims(EntityAnimator anim)
    {
        for (EntityAnim ea : anim.active)
        {
            if (ea.active) AnimFrame(ea);
        }
    }
    public void SelectAnims(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        
    }
    @Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
        EntityMD in = (EntityMD)entityIn;
        ResetTransforms();
        if (BloomQueue.cPass == BloomQueue.Pass.Normal)
            SelectAnims(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        ApplyAnims(in.animator);
        animate(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
    public void setRotationAnglesExternal(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, EntityAnimator animator)
    {
        ResetTransforms();
        ApplyAnims(animator);
    }
    public void animate(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
        return;
    }
}
