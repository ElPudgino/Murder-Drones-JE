package pzi.md_je.entities.DD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.entities.EntityMD;
import pzi.md_je.generic.HeatManager;
import pzi.md_je.generic.IChargeHolder;
import pzi.md_je.generic.IFlyer;
import pzi.md_je.generic.IHeatHolder;
import pzi.md_je.generic.IOilHolder;
import pzi.md_je.generic.weapons.InnateWeapon;
import pzi.md_je.gui.DDGui;
import pzi.md_je.gui.PrettyGUI;
import pzi.md_je.loading.EntityRenderHandler;
import pzi.md_je.rendering.BloomQueue;

public class EntityDD extends EntityMD implements IChargeHolder, IHeatHolder, IOilHolder, IFlyer
{
    protected StatsDD statsDD;
    protected FlightDD flightDD;

    public EntityDD(World worldIn) 
    {
        super(worldIn);
        setSize(0.8f, 2.5f);
        statsDD = new StatsDD(this, null);
        flightDD = new FlightDD(statsDD);
    }  

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    @Override
    protected void applyEntityAttributes() 
    {
        super.applyEntityAttributes(); 
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0f);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0f);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0f);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0f);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0f);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0f);
    }

    public StatsDD getStats() 
    {
        return statsDD;
    }
    public boolean isDashing()
    {
        return false;
    }

    @Override
    public float getEyeHeight()
    {
        return 2.3f;
    }

    @Override
    public boolean isPushedByWater()
    {
        return false;
    }
    public boolean isAiming(EnumHand hand)
    {
        return false;
    }
    public boolean isSneaking()
    {
        return false;
    }
    public float GetCharge()
    {
        return statsDD.GetCharge();
    }   
    public float AddCharge(float delta)
    {
        return statsDD.AddCharge(delta);
    }

    @Override
    public float GetCapacity() 
    {
        return statsDD.GetCapacity();
    }

    @Override
    public float GetCurrentOil() 
    {
        return statsDD.GetCurrentOil();
    }

    @Override
    public float Input(float amount) 
    {
        return statsDD.Input(amount);
    }
    @Override
    public HeatManager GetHeatManager() 
    {
        return statsDD.GetHeatManager();
    }
    @Override 
    public void SetOil(float val)
    {
        statsDD.SetOil(val);
    }
    @Override 
    public FlightDD GetFlightController()
    {
        return flightDD;
    }

}
