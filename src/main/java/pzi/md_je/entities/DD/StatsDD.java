package pzi.md_je.entities.DD;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.generic.HeatManager;
import pzi.md_je.generic.IChargeHolder;
import pzi.md_je.generic.IHeatHolder;
import pzi.md_je.generic.IOilHolder;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.util.DamageUtils;
import pzi.md_je.util.math;

public class StatsDD implements IChargeHolder, IHeatHolder, IOilHolder
{
    public static final float maxCharge = 2.5e6f;
    public static final float maxOil = 6f; // in B
    public static final float maxBlood = 4f;
    public float charge = maxCharge;
    public float oil = 0f;
    public float blood = 0f;
    public HeatManager heatManager;
    public boolean criticalState = false;
    public int criticalTicks = 0;

    public EntityLivingBase owner;
    public DDMorph oMorph;

    public static final float ticksPerB = 42000f;
    public static final float flyTicksPerB = 11000f;
    public static final float takeOffCost = 0.012f;
    public static final float BPerHp = 0.0013f;
    public static final float TPerHp = 1.8f;
    public static final float TPerFlightTick = 0.07f;
    public static final float TPerTakeOff = 14f;
    public static final float hpPerSec = 16f;
    public static final float daylightTempPerTick = 1.3f;
    public static final float tempPerFireDmg = 3.5f;
    public static final float fireDmgBlock = 3f;
    public static final float targetCoreTemp = 40f;
    public static final float OHWhiteStart = 120f;
    public static final float OHWhiteEnd = 100f;
    public static final float OHYellowStart = 200f;
    public static final float OHYellowEnd = 170f;
    public static final float OHRedStart = 310f;
    public static final float OHRedEnd = 260f;
    public static final float OHWhiteHealMult = 0.5f;
    public static final float OHYellowHealMult = 0.1f;
    public static final float deathCoreTempIncrease = 666f;
    public static final float deathComfOil = 2.5f;
    public static final float deathTempPerMb = 0.6f; // increase in core temp per missing mb
    public static final float thermalConductivity = 0.006f;
    public static final float heatCapRatio = 3f; // Hull heat cap / core heat cap
    public static final float coreDegreesPerMb = 50f;
    public static final float oilCoolingSpeed = 0.3f; // Degrees per tick per at comfTemp with full oil
    public static final float whiteOilLevel = 2f;
    public static final float yellowOilLevel = 0.9f;
    public static final float redOilLevel = 0.4f;
    public static final float minCoolOil = 0.1f;
    public static final float bloodToOilRatio = 0.4f;
    public static final float bloodConversionSpeed = 1.0f;
    public static final float genericDamageBlock = 2f;
    public static final float genericDamageMult = 0.8f;
    public static final float baseArmor = 12f;
    public static final float baseArmorToughness = 5f;
    public static final float baseKnockbackResist = 1f;
    public static final float maxHP = 120f;
    public static final float baseMovespeedMult = 1.25f;
    public static final float dashMovespeedMult = 1.7f; // when sprinting in combat mode
    public static final float dashStepHeight = 1.1f;
    public static final float deathResistHP = 80f;
    public static final int criticalRecoveryTicks = 150;
    public static final float outOfOilHeat = 0.12f; 

    public static final float bloodPerKill = 2.5f; // for players and villagers

    public DamageSource getFeedDamageSource()
    {
        return new EntityDamageSource("dd_feed", owner).setDamageBypassesArmor().setDamageIsAbsolute();
    }
    public static float feedDamage = 4f;
    public static float feedEff = 0.3f; // B per feed

    public static DamageSource overheatDamageType = new DamageSource("dd_overheat").setDamageBypassesArmor().setDamageIsAbsolute();
    public static float overheatDamage = 0.0015f; // per sec per degree above red overheat
    public static float overheatDamageBase = 0.5f;

    protected int tickCounter;

    public StatsDD(EntityLivingBase e, IMorph morph)
    {
        if (e instanceof EntityPlayer)
        {
            oMorph = (DDMorph)morph;
            owner = e;
        }
        else if (e instanceof EntityDD)
        {
            owner = e;
        }
        else
        {   
            MD.logger.error("Tried constructing statsDD with an invalid owner");
        }
        heatManager = new HeatManager();
    }

    public void Update()
    {
        tickCounter++;
        tickCounter = tickCounter % 20;

        HeatManager h = GetHeatManager();

        if (isDay() && owner.world.canSeeSky(new BlockPos(math.getHeadPos(owner))))
        {
            OnDaylightTick();
        }

        if (GetFlightController().isFlying())
        { 
            Input(-1f/StatsDD.flyTicksPerB); 
            h.temp += TPerFlightTick;
        }
        else Input(-1f/StatsDD.ticksPerB);

        if (owner instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)owner;
            p.getFoodStats().setFoodLevel(10);
            if (owner.world.isRemote)
                p.getFoodStats().setFoodSaturationLevel(10);
        }
        
        if (!criticalState && owner.getHealth() < owner.getMaxHealth() && h.overheat != OverheatLevel.Red)
        {
            float heal = MathHelper.clamp(GetHealAmount(), 0f, owner.getMaxHealth() - owner.getHealth());
            if (h.overheat == OverheatLevel.White) heal *= OHWhiteHealMult;
            if (h.overheat == OverheatLevel.Yellow) heal *= OHYellowHealMult;
            if (!owner.world.isRemote) owner.heal(heal);
            Input(-heal*StatsDD.BPerHp);
            heatManager.temp += heal * TPerHp;
        }

        if (blood > 0f)
        {
            float db = Math.min(blood, StatsDD.bloodConversionSpeed/1000f);
            db = Math.min(db, (StatsDD.maxOil - oil)/StatsDD.bloodToOilRatio);
            blood -= db;
            oil += db * StatsDD.bloodToOilRatio;
        }

        float dt = (h.temp - h.coreTemp) * StatsDD.thermalConductivity;
        h.coreTemp += dt;
        h.temp -= dt / StatsDD.heatCapRatio;

        if (oil <= minCoolOil)
        {
            h.coreTemp += outOfOilHeat;
        }
        else if (h.coreTemp > StatsDD.targetCoreTemp && oil > StatsDD.minCoolOil)
        {
            dt = StatsDD.oilCoolingSpeed * ((float)Math.sqrt((h.coreTemp - StatsDD.targetCoreTemp + 10f)/10f)) * 
            MathHelper.clamp(oil / StatsDD.whiteOilLevel, 0f, 1f);
            if (dt > 0)
            {
                h.coreTemp = Math.max(h.coreTemp - dt, StatsDD.targetCoreTemp);
                oil -= dt * 0.001f / StatsDD.coreDegreesPerMb;
            }
        }

        OverheatLevel oh_prev= h.overheat;
        if (h.coreTemp < StatsDD.OHWhiteEnd) h.overheat = OverheatLevel.None;
        else if (h.coreTemp > StatsDD.OHRedStart) h.overheat = OverheatLevel.Red;
        else if (h.overheat == OverheatLevel.Red && h.coreTemp < StatsDD.OHRedEnd) h.overheat = OverheatLevel.Yellow;
        else if (h.overheat == OverheatLevel.Yellow && h.coreTemp < StatsDD.OHYellowEnd) h.overheat = OverheatLevel.White; 
        else if (h.overheat == OverheatLevel.White && h.coreTemp > StatsDD.OHYellowStart) h.overheat = OverheatLevel.Yellow;
        else if (h.overheat == OverheatLevel.None && h.coreTemp > StatsDD.OHWhiteStart) h.overheat = OverheatLevel.White;
        if (oh_prev != h.overheat && oMorph != null) oMorph.dirty = true; 

        if (tickCounter == 0)
            OverheatUpdate(h.overheat);

        if (criticalState) CriticalTick();
    }

    protected void OverheatUpdate(OverheatLevel o)
    {
        HeatManager h = GetHeatManager();
        if (o == OverheatLevel.Red)
        {
            if (!owner.world.isRemote && tickCounter == 0)
            {
                if (!owner.getIsInvulnerable()) 
                {
                    DamageUtils.DamageNoIFrames(owner, overheatDamageType, overheatDamageBase + overheatDamage * (h.coreTemp - OHRedStart));
                }
                PotionEffect effect = new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:slowness"), 40, 2);
                owner.addPotionEffect(effect);        
            }
        }
    }

    public void OnHurt(LivingHurtEvent e)
    {
        DamageSource src = e.getSource();
        if (src == DamageSource.FALL)
        {
            float amount = e.getAmount();
            if (amount < 10f) e.setCanceled(true);
            else e.setAmount((amount - 10f) * 0.5f);
        }
        if (!src.isDamageAbsolute() && !src.isUnblockable())
        {
            e.setAmount((e.getAmount() - genericDamageBlock) * genericDamageMult);
        }
    }
    public void OnAttacked(LivingAttackEvent e)
    {
        DamageSource src = e.getSource();
        if (e.getAmount() < genericDamageBlock && !src.isUnblockable() && !src.isDamageAbsolute()) e.setCanceled(true);
        if (src == DamageSource.FALL)
        {
            if (GetFlightController().areWingsOut()) e.setCanceled(true);  
            return;
        }
        if (src.isFireDamage())
        {
            if (e.getAmount() < fireDmgBlock) e.setCanceled(true);
            return;
        }
    }
    public void OnTookDamage(LivingDamageEvent e)
    {
        DamageSource src = e.getSource();
        if (src.isFireDamage())
        {
            GetHeatManager().temp += e.getAmount() * StatsDD.tempPerFireDmg;
            if (e.getAmount() < StatsDD.fireDmgBlock) e.setCanceled(true);
            else e.setAmount(e.getAmount() - StatsDD.fireDmgBlock);
        }   
    }

    protected boolean isDay()
    {
        float angle = owner.world.getCelestialAngle(1.0F);
        return (angle < 0.25F || angle > 0.75F);
    }
    protected void OnDaylightTick()
    {
        GetHeatManager().temp += StatsDD.daylightTempPerTick;
        if (!owner.world.isRemote) owner.setFire(2);
    }
    protected FlightDD GetFlightController()
    {
        if (oMorph != null) return oMorph.GetFlightController();
        return ((EntityDD)owner).GetFlightController();
    }
    public boolean OnDeath(DamageSource source)
    {
        if (criticalState)
        {
            criticalState = false;
            return false;
        }
        float debt = Math.max(deathComfOil - oil, 0f);
        float dt = debt * 1000f * deathTempPerMb;
        heatManager.temp += dt;
        oil = Math.max(0, oil - deathComfOil);
        criticalState = true;
        if (oMorph != null) oMorph.SetCombatMode(false);
        if (!owner.world.isRemote) 
        {
            owner.setHealth(deathResistHP);
            if (oMorph != null) oMorph.SyncFromServer();
        }
        return true;
    }
    public void RecoverFromCritical()
    {
        if (owner.world.isRemote) return;
        criticalState = false;
        if (oMorph != null) oMorph.SyncFromServer();
    }
    public void CriticalTick()
    {
        if (oil >= minCoolOil && heatManager.overheat == OverheatLevel.None)
        {
            criticalTicks++;
            if (criticalTicks >= criticalRecoveryTicks)
            {
                RecoverFromCritical();
                criticalTicks = 0;
            }
        }
        else if (criticalTicks > 0) criticalTicks--;
    }

    public float GetHealAmount()
    {
        return 0.05f * hpPerSec * MathHelper.clamp((oil - yellowOilLevel) / (whiteOilLevel - yellowOilLevel), 0f, 1f);
    }
    public float GetCharge()
    {
        return charge;
    }   
    public float AddCharge(float delta)
    {
        return charge = MathHelper.clamp(charge + delta, 0f, maxCharge);
    }
    @Override
    public float GetCapacity() 
    {
        return maxOil;
    }
    @Override
    public float GetCurrentOil() 
    {
        return oil;
    }
    @Override
    public float Input(float amount) 
    {
        return oil = MathHelper.clamp(oil + amount, 0, maxOil);
    }
    @Override
    public HeatManager GetHeatManager() 
    {
        return heatManager;
    }
    @Override 
    public void SetOil(float val)
    {
        oil = val;
    }
    public NBTTagCompound ToNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setFloat("charge", charge);
        tag.setFloat("oil", oil);
        tag.setFloat("bl", blood);
        tag.setTag("hm", heatManager.ToNBT());
        tag.setBoolean("cr", criticalState);
        tag.setInteger("crt", criticalTicks);
        return tag;
    }

    public void FromNBT(NBTTagCompound tag)
    {
        charge = tag.getFloat("charge");
        oil = tag.getFloat("oil");
        blood = tag.getFloat("bl");
        heatManager.FromNBT(tag.getCompoundTag("hm"));
        criticalState = tag.getBoolean("cr");
        criticalTicks = tag.getInteger("crt");
    }

    public static boolean isValidFeedTarget(EntityLivingBase e)
    {
        boolean f = false;
        if (e instanceof EntityPlayer)
        {
            f = true;
            EntityPlayer p = (EntityPlayer)e;
            if (!p.isSneaking()) f = false;
            else
            {
                IMorph m = PlayerMorphs.GetMorph(p);
                if (m != null && m.GetMorph() == Morphs.DD) f = false;
            }         
        }
        if (e instanceof EntityVillager) f = true;
        return f && e.attackable() && e.hurtResistantTime == 0;
    }
    

    public enum AbilityType
    {
        Fly,
        FlyStop,
        UnfoldWings,
        FoldWings,
        TakeOff,
        CombatToggle,
        WeaponSwap,
        Feed
    }
}
