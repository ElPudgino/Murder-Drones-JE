package pzi.md_je.anim;

import pzi.md_je.rendering.AnimLoader.AnimationData;

public class EntityAnim 
{
    public EntityAnim(AnimationData anim)
    {
        curData = anim;
        prevTime = -1f;
    }
    public float curTime = 0;
    public float prevTime;
    public AnimationData curData;
    public float speed = 1f;
    public float weight = 1f;
    public boolean continued = true;
    public boolean active;
}
