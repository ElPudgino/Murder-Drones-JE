package pzi.md_je.anim;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.MathHelper;
import pzi.md_je.rendering.AnimLoader.AnimationData;

public class EntityAnimator 
{
    protected float prevTimeAlive = 0;
    protected List<EntityAnim> active = new ArrayList<>();

    protected EntityAnim FindAnim(AnimationData anim)
    {
        for (EntityAnim ea : active)
        {
            if (ea.curData == anim) return ea;  
        }
        return null;
    }
    public EntityAnim StartAnim(AnimationData anim, float speed, float weight)
    {
        EntityAnim ea = FindAnim(anim);
        if (ea == null)
        {
            ea = new EntityAnim(anim);
            active.add(ea);
            ea.active = true;
            ea.speed = speed;
            ea.weight = weight;
            ea.prevTime = prevTimeAlive;
        }
        else
        {
            ea.prevTime = prevTimeAlive;
            ea.curTime = 0;
            ea.weight = weight;
            ea.speed = speed;
            ea.active = true;
        }
        return ea;
    }
    public void StopAnim(AnimationData anim)
    {
        EntityAnim ea = FindAnim(anim);
        if (ea != null)
        {
            ea.active = false;
        }
    }
    public void ContinueAnim(AnimationData anim, float speed, float weight)
    {
        EntityAnim ea = FindAnim(anim);
        if (ea == null)
        {
            StartAnim(anim, speed, weight);
        }
        else
        {
            ea.active = true;
            ea.continued = true;
            ea.speed = speed;
            ea.weight = weight;
        }
    }
    public void SetAnimProgress(AnimationData anim, float weight, float progress)
    {
        EntityAnim ea = FindAnim(anim);
        if (ea == null)
        {
            ea = StartAnim(anim, 0f, weight);
        }
        else
        {
            ea.active = true;
            ea.continued = true;
            ea.speed = 0f;
            ea.weight = weight;
        }
        ea.curTime = ea.curData.length * (progress % 1f);
    }
    public void UpdateAnims()
    {
        for (EntityAnim anim : active)
        {
            if (anim.curData.loop)
            {
                if (anim.continued)
                {
                    anim.continued = false;
                }
                else anim.active = false;
            }
            else
            {
                if (anim.curTime >= anim.curData.length)
                {
                    anim.active = false;
                }
            }
        }
    }
    public void ProgressAnims(float timeAlive)
    {
        for (EntityAnim eanim : active)
        {
            if (eanim.prevTime < 0 || !eanim.active) 
            {
                eanim.prevTime = timeAlive;
                continue;
            }
            float dt = timeAlive - eanim.prevTime;
            dt = dt * eanim.speed;
            eanim.curTime += dt * 0.05f;
            eanim.prevTime = timeAlive;
            if (eanim.curTime > eanim.curData.length) 
            {
                if (eanim.curData.loop) eanim.curTime = 0;
                else eanim.curTime = eanim.curData.length;
            }
        }
        UpdateAnims();
        prevTimeAlive = timeAlive;
    }
    public boolean IsActive(AnimationData anim)
    {
        EntityAnim ea = FindAnim(anim);
        if (ea == null) return false;
        return ea.active;
    }
}
