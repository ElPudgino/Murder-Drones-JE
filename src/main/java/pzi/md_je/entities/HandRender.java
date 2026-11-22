package pzi.md_je.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.rendering.BloomQueue;

public abstract class HandRender<T extends Entity, G extends AnimModelBase>
{
    protected EntityAnimator animator;
    public HandRender()
    {
        animator = new EntityAnimator();
    }
    public EntityAnimator GetAnimator()
    {
        return animator;
    }

    public void RenderHands(T dummy, EntityPlayer owner, float swing)
    {
        checkModelCompiled(dummy);
        PreAnimate(dummy, owner);
        float partial = Minecraft.getMinecraft().getRenderPartialTicks();
        float armSwingAmount = owner.prevLimbSwingAmount + (owner.limbSwingAmount - owner.prevLimbSwingAmount) * partial;
        animate(dummy,owner,owner.ticksExisted + partial, swing);
        GetModel().setRotationAnglesExternal(owner.limbSwing, armSwingAmount, owner.ticksExisted + partial,
             0,0, 1, dummy, animator);
        PostAnimate(dummy, owner);
    }
    protected void PreAnimate(T dummy, EntityPlayer owner)
    {

    }
    protected void PostAnimate(T dummy, EntityPlayer owner)
    {

    }
    protected void animate(T dummy, EntityPlayer owner, float ageInTicks, float swing)
    {
        if (BloomQueue.Pass.Normal == BloomQueue.cPass) animator.ProgressAnims(ageInTicks);       
    }
    protected abstract G GetModel();
    public abstract void checkModelCompiled(T dummy);
}
