package pzi.md_je.entities;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import pzi.md_je.MD;
import pzi.md_je.anim.EntityAnimator;

public abstract class EntityMD extends EntityMob
{
    public EntityAnimator animator;
    public EntityMD (World worldIn)
    {
        super(worldIn);
        animator = new EntityAnimator();
    }

}
