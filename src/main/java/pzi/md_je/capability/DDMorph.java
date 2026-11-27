package pzi.md_je.capability;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.EntityDDMorph;
import pzi.md_je.entities.DD.FlightDD;
import pzi.md_je.entities.DD.HandRenderDD;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.entities.DD.RenderEntityDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.events.HandEvent;
import pzi.md_je.generic.HeatManager;
import pzi.md_je.generic.IChargeHolder;
import pzi.md_je.generic.IFlyer;
import pzi.md_je.generic.IHeatHolder;
import pzi.md_je.generic.IOilHolder;
import pzi.md_je.generic.IWeaponHolder;
import pzi.md_je.generic.weapons.DD_HMG;
import pzi.md_je.generic.weapons.DD_Hand;
import pzi.md_je.generic.weapons.InnateWeapon;
import pzi.md_je.gui.DDGui;
import pzi.md_je.gui.PrettyGUI;
import pzi.md_je.loading.ClientProxy;
import pzi.md_je.loading.EntityRenderHandler;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.network.to_server.ability.dd.SDDFeedPacket;
import pzi.md_je.player.Morphs;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.util.Collision;
import pzi.md_je.util.math;

public class DDMorph implements IMorph, IChargeHolder, IHeatHolder, IOilHolder, IFlyer, IWeaponHolder
{
    public EntityPlayer owner;
    protected EntityDDMorph dummy;
    public HandRenderDD handRender;

    protected UUID hp_uuid = UUID.fromString("4671def0-fbac-4a0a-94b6-6e2a66072286");
    protected AttributeModifier hp_mod;
    protected UUID dmg_uuid = UUID.fromString("b97973e2-6fab-4c02-837e-630035474f21");
    protected AttributeModifier dmg_mod;
    protected UUID armor_uuid = UUID.fromString("731acf47-9410-4198-8beb-0391940b1c49");
    protected AttributeModifier armor_mod;
    protected UUID armor_t_uuid = UUID.fromString("93368fa8-7bdc-4932-bc4c-8506c8a250da");
    protected AttributeModifier armor_t_mod;
    protected UUID kb_res_uuid = UUID.fromString("06291b3b-c777-4268-8a78-ff62490702d6");
    protected AttributeModifier kb_res_mod;
    protected UUID base_movespeed_uuid = UUID.fromString("8e7c1658-ec55-429e-b40f-a8edf1cd4aaa");
    protected AttributeModifier base_movespeed_mod;
    protected UUID dash_movespeed_uuid = UUID.fromString("8e575aed-ebcd-4ad5-b486-ae542896ffda");
    protected AttributeModifier dash_movespeed_mod;
    private long updateCounter = 0;
    public boolean dirty = true;

    public StatsDD statsDD;
    public FlightDD flightController;

    public final List<InnateWeapon<DDMorph, ModelEntityDD>> availableWeapons = Arrays.asList(
            new pzi.md_je.generic.weapons.DD_Claws(this),
            new pzi.md_je.generic.weapons.DD_HMG(this));

    private boolean combatMode;
    private InnateWeapon<DDMorph, ModelEntityDD> defaultWeapon;
    private InnateWeapon<DDMorph, ModelEntityDD> currentWeaponR;
    

    public EntityPlayer GetOwner() {return owner;}
    public EntityAnimator GetAnimator() {return dummy.animator;}
    public EntityAnimator GetHandAnimator() {return handRender.GetAnimator();}
    public DDMorph() {}
    public DDMorph(EntityPlayer o)
    {
        owner = o;
        statsDD = new StatsDD(o, this);
        flightController = new FlightDD(statsDD);
        currentWeaponR = availableWeapons.get(0);
        defaultWeapon = new DD_Hand(this);
        if (o.world.isRemote)
        {
            dummy = new EntityDDMorph(o.world);
            dummy.morph = this;
            handRender = new HandRenderDD();
            if (o == Minecraft.getMinecraft().player) DDGui.instance.CleanUp();
        }
        else
        {
            hp_mod = new AttributeModifier(hp_uuid, "dd_hp_mod", StatsDD.maxHP-20f, 0);
            dmg_mod = new AttributeModifier(dmg_uuid, "dd_dmg_mod", 1.5f, 1);
            armor_mod = new AttributeModifier(armor_uuid, "dd_armor_mod", StatsDD.baseArmor, 0);
            armor_t_mod = new AttributeModifier(armor_t_uuid, "dd_armor_t_mod", StatsDD.baseArmorToughness, 0);
            kb_res_mod = new AttributeModifier(kb_res_uuid, "dd_kbres_mod", StatsDD.baseKnockbackResist, 0);
            base_movespeed_mod = new AttributeModifier(base_movespeed_uuid, "dd_ms_mod", StatsDD.baseMovespeedMult - 1f, 2);
            dash_movespeed_mod = new AttributeModifier(dash_movespeed_uuid, "dd_ms_ds_mod", StatsDD.dashMovespeedMult - 1f, 2);
        }
    }
    public AxisAlignedBB GetRenderBB()
    {
        if (flightController.areWingsOut()) return new AxisAlignedBB(-3f, 0f, -3f, 3f, 4.3f, 3f);
        return new AxisAlignedBB(-1.4f, 0f, -1.4f, 1.4f, 3.3f, 1.4f);
    }
    public Vec2f GetSize() // TODO: check numbers for dash and critical
    {   
        if (statsDD.criticalState) return new Vec2f(0.8f, 1.6f);
        else if (isDashing()) return new Vec2f(0.75f, 1.77f);
        else if (!flightController.isFlying() && owner.isSneaking())
        {
            return GetCrouchSize();
        }
        double angle = flightController.GetFwdAngle(new Vec3d(owner.motionX, owner.motionY, owner.motionZ));
        return new Vec2f((float)(-0.7f * Math.abs(Math.cos(angle)) + 1.5f), (float)(1.3f * Math.abs(Math.cos(angle)) + 1.3f));
    }
    public Vec2f GetCrouchSize()
    {
        return new Vec2f(0.8f, 1.9f);
    }
    public float GetEyeHeight()
    {
        if (statsDD.criticalState) return 1.36f;
        else if (isDashing()) return 1.43f;
        else if (!flightController.isFlying() && (owner.isSneaking() || owner.height == GetCrouchSize().y))
        {
            return GetCrouchEyeHeight();
        }
        double angle = flightController.GetFwdAngle(new Vec3d(owner.motionX, owner.motionY, owner.motionZ));
        double prevAngle = flightController.GetFwdAngle(flightController.prevVel1);
        float h = (float)(1.6f * Math.cos(angle * GraphicsUtils.GetPartialTick() + prevAngle * (1f-GraphicsUtils.GetPartialTick())) + 0.7f);
        return h;
    }
    public float GetCrouchEyeHeight()
    {
        return 1.7f;
    }
    public void Sync() // only clientside
    {
        dummy.prevRenderYawOffset = owner.prevRenderYawOffset;
        dummy.renderYawOffset = owner.renderYawOffset;
        dummy.rotationYawHead = owner.rotationYawHead;
        dummy.rotationPitch = owner.rotationPitch;
        dummy.prevRotationYawHead = owner.prevRotationYawHead;
        dummy.prevRotationPitch = owner.prevRotationPitch;
        dummy.posX = owner.posX;
        dummy.posY = owner.posY;
        dummy.posZ = owner.posZ;
        dummy.prevPosX = owner.prevPosX;
        dummy.prevPosY = owner.prevPosY;
        dummy.prevPosZ = owner.prevPosZ;
        dummy.ticksExisted = owner.ticksExisted;
        dummy.limbSwing = owner.limbSwing;
        dummy.limbSwingAmount = owner.limbSwingAmount;
        dummy.prevLimbSwingAmount = owner.prevLimbSwingAmount;
        dummy.swingProgress = owner.swingProgress;
        dummy.prevSwingProgress = owner.prevSwingProgress;
    }
    public void RenderAt(double x, double y, double z, float partial)
    {
        handRender.checkModelCompiled(dummy);
        Sync();
        EntityRenderHandler.DD.doRender(dummy, x, y, z, owner.rotationYaw, partial);
    }
    public PrettyGUI GetGui()
    {
        return DDGui.instance;
    }
    public void RenderHands(float swing)
    {
        handRender.RenderHands(dummy, owner, swing);
    }
    public void RenderTick(float dt)
    {
    }
    public void OnUpdate(Side side)
    {
        if (!owner.world.isRemote)
        {
            if (!owner.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(hp_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(hp_mod);
            }
            if (!owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(dmg_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(dmg_mod);
            }
            if (!owner.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(armor_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(armor_mod);
            }
            if (!owner.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(armor_t_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(armor_t_mod);
            }
            if (!owner.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).hasModifier(kb_res_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(kb_res_mod);
            }
            if (!owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(base_movespeed_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(base_movespeed_mod);
            }
            if (isDashing())
            {
                if (!owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(dash_movespeed_mod))
                {
                    owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(dash_movespeed_mod);
                }
            }
            else 
            {
                if (owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(dash_movespeed_mod))
                {
                    owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(dash_movespeed_mod);
                }
            }

            if (updateCounter % 20 == 0 && dirty)
            {
                SyncFromServer();
                dirty = false;
            }
            else if (updateCounter % 20 == 0)
            {
                CMorphPacket p = new CMorphPacket(owner, false);
                PacketHandler.INSTANCE.sendTo(p, (EntityPlayerMP)owner);   
            }

        }
        if (isDashing()) owner.stepHeight = StatsDD.dashStepHeight;
        else owner.stepHeight = 0.6f;

        flightController.Update();

        statsDD.Update();
        UpdateWeapons();

        updateCounter ++;
    }
    public void SyncFromServer()
    {
        CMorphPacket p = new CMorphPacket(owner, false);
        PacketHandler.INSTANCE.sendToAllTracking(p, owner);
        PacketHandler.INSTANCE.sendTo(p, (EntityPlayerMP)owner);
    }
    public void OnRemove()
    {
        flightController.stopFlying();
        if (!owner.world.isRemote)
        {
            if (owner.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(hp_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(hp_mod);;
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(dmg_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(dmg_mod);
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(armor_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(armor_mod);
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(armor_t_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(armor_t_mod);
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).hasModifier(kb_res_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(kb_res_mod);
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(base_movespeed_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(base_movespeed_mod);
            }
            if (owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(dash_movespeed_mod))
            {
                owner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(dash_movespeed_mod);
            }
        }      
    }
    public void OnHurt(LivingHurtEvent e)
    {
        statsDD.OnHurt(e);
    }
    public void OnAttacked(LivingAttackEvent e)
    {
        statsDD.OnAttacked(e);
    }
    public void OnTookDamage(LivingDamageEvent e)
    {
        statsDD.OnTookDamage(e);
    }
    public Morphs GetMorph()
    {
        return Morphs.DD;
    }
    public boolean isAiming(EnumHand hand)
    {
        return GetWeapon(hand) instanceof DD_HMG;
    }
    public boolean isDashing()
    {
        return !statsDD.criticalState && statsDD.heatManager.overheat != OverheatLevel.Red && !owner.isSneaking() && owner.isSprinting() && IsCombatMode() && !flightController.isFlying();
    }
    public void OnRespawned()
    {
        statsDD.criticalState = false;
        statsDD.heatManager.temp = StatsDD.targetCoreTemp;
        statsDD.heatManager.coreTemp = StatsDD.targetCoreTemp;
        statsDD.oil = 1f;
        statsDD.blood = 0f;
        if (!owner.world.isRemote)
        {
            owner.setHealth(owner.getMaxHealth());
            SyncFromServer();
        }
        else DDGui.instance.CleanUp();
    }
    public NBTTagCompound ToNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("stats", statsDD.ToNBT());
        tag.setTag("fl", flightController.ToNBT());
        tag.setTag("weapons", WeaponsToNBT(true));
        return tag;
    }
    public void FromNBT(NBTTagCompound tag)
    {
        statsDD.FromNBT(tag.getCompoundTag("stats"));
        flightController.FromNBT(tag.getCompoundTag("fl"));
        WeaponsFromNBT(tag.getCompoundTag("weapons"));
    }
    public NBTTagCompound ToSyncNBT(boolean full)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("st", statsDD.ToNBT());
        tag.setTag("fl", flightController.ToNBT());
        tag.setTag("weapons", WeaponsToNBT(full));
        return tag;
    }
    public void FromSyncNBT(NBTTagCompound tag)
    {
        statsDD.FromNBT(tag.getCompoundTag("st"));
        flightController.FromNBT(tag.getCompoundTag("fl"));
        WeaponsFromNBT(tag.getCompoundTag("weapons"));
    }
    protected NBTTagCompound WeaponsToNBT(boolean full)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("combat", combatMode);
        tag.setString("selected", currentWeaponR.GetId());
        if (full)
        {
            for (InnateWeapon<DDMorph,ModelEntityDD> weapon : availableWeapons)
                tag.setTag(weapon.GetId(), weapon.ToNBT());
        }
        else 
            tag.setTag(currentWeaponR.GetId(), currentWeaponR.ToNBT());

        return tag;
    }

    protected void WeaponsFromNBT(NBTTagCompound tag)
    {
        combatMode = tag.getBoolean("combat");
        for (int i = 0; i < availableWeapons.size(); i++)
        {
            InnateWeapon<DDMorph, ModelEntityDD> weapon = availableWeapons.get(i);
            if (weapon.GetId().equals(tag.getString("selected"))) currentWeaponR = weapon;
            if (tag.hasKey(weapon.GetId()))
                weapon.FromNBT(tag.getCompoundTag(weapon.GetId()));
        }
        if (combatMode) currentWeaponR.OnSelect();
    }
    @Override 
    public boolean HideHeldItem(EnumHand hand)
    {
        return IsCombatMode();
    }
    @Override
    public boolean OnAttackEntity(AttackEntityEvent event) 
    {
        if (!combatMode && owner.world.isRemote && handRender != null)
        {
        }  
        return combatMode;
    }  
    @Override
    public boolean OnLeftClickBlock(LeftClickBlock event) 
    {
        if (!combatMode && owner.world.isRemote && handRender != null)
        {
        }  
        return combatMode;
    }
    @Override
    public void OnLeftClickEmpty(LeftClickEmpty event) 
    {
        if (!combatMode && owner.world.isRemote && handRender != null)
        {
        }  
    }
    public boolean IsCombatMode() { return combatMode; }
    public void OnLeftClick()
    {
        InnateWeapon<DDMorph, ModelEntityDD> wep = GetWeapon(EnumHand.MAIN_HAND);
        wep.OnLeftClick();
        wep.OnLeftClickHold();
    }
    public void OnRightClick()
    {
        InnateWeapon<DDMorph, ModelEntityDD> wep = GetWeapon(EnumHand.MAIN_HAND);
        wep.OnRightClick();
        wep.OnRightClickHold();
        if (ClientProxy.keySpecial.isKeyDown())
        {
            TryUseFeed();
        }
    }
    public InnateWeapon<DDMorph, ModelEntityDD> GetWeapon(EnumHand hand)
    {
        if (!IsCombatMode()) return defaultWeapon;
        if (hand == EnumHand.MAIN_HAND) return currentWeaponR;
        return defaultWeapon;
    }

    public void SetCombatMode(boolean enabled)
    {   
        if (statsDD.criticalState) enabled = false;
        if (combatMode == enabled) return;
        combatMode = enabled;
        if (enabled) currentWeaponR.OnSelect();
        else currentWeaponR.OnDeselect();
    }

    public void SelectWeapon(InnateWeapon<DDMorph, ModelEntityDD> wep)
    {
        if (currentWeaponR.GetId() == wep.GetId()) return;
        currentWeaponR.OnDeselect();
        currentWeaponR = wep;
        currentWeaponR.OnSelect();
    }
    public boolean SelectWeapon(int id)
    {
        if (currentWeaponR.GetNumericId() == id) return true;
        for (InnateWeapon<DDMorph, ModelEntityDD> a : availableWeapons)
        {
            if (a.GetNumericId() == id) {SelectWeapon(a); return true;}
        }
        return false;
    }
    public void NextWeapon()
    {
        int ind = 0;
        for (int i = 0; i < availableWeapons.size();i++) if (availableWeapons.get(i).GetId() == currentWeaponR.GetId()) {ind = i; break;}
        SelectWeapon(availableWeapons.get((ind+1)%availableWeapons.size()));
    }
    public void TryUseFeed()
    {
        EntityLivingBase e = GetFeedTarget();
        if (e == null) return;
        if (StatsDD.isValidFeedTarget(e))
        {
            PacketHandler.INSTANCE.sendToServer(new SDDFeedPacket(e));
            AddBlood(StatsDD.feedEff);
        }
    }
    public void AddBlood(float amount)
    {
        statsDD.blood = Math.min(statsDD.blood + amount, StatsDD.maxBlood);
    }
    public EntityLivingBase GetFeedTarget()
    {
        return Collision.RaycastEntityFromHead(owner, 1.2f, 0.2f, 0d);
    }
    public void Feed(EntityLivingBase e)
    {
        if (!owner.world.isRemote)
        {
            e.attackEntityFrom(statsDD.getFeedDamageSource(), StatsDD.feedDamage);
            statsDD.blood = Math.min(statsDD.blood + StatsDD.feedEff, StatsDD.maxBlood);
            PacketHandler.INSTANCE.sendTo(new CMorphPacket(owner, false), (EntityPlayerMP)owner);
        }
    }
    public boolean CanUseFeed()
    {
        if (!ClientProxy.keySpecial.isKeyDown()) return false;
        EntityLivingBase e = GetFeedTarget();
        return e != null && StatsDD.isValidFeedTarget(e);
    }

    private void UpdateWeapons()
    {
        for (InnateWeapon<DDMorph, ModelEntityDD> weapon : availableWeapons) weapon.Update();
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

    public float GetStoredBlood()
    {
        return statsDD.blood;
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
        return flightController;
    }
    @Override // called on server after receiving packet from client
    public void OnWeaponAction(int wepid, int actionType, boolean server) 
    {
        if (server) 
        {
            if (wepid != GetWeapon(EnumHand.MAIN_HAND).GetNumericId()) return;
            GetWeapon(EnumHand.MAIN_HAND).OnActionServer(actionType);
        }
        else 
        {
            if (wepid != GetWeapon(EnumHand.MAIN_HAND).GetNumericId()) 
            {
                if (!SelectWeapon(wepid))
                {
                    MD.logger.info("Server sent a weapon use packet with unavailable weapon");
                    return;
                }
            }
            GetWeapon(EnumHand.MAIN_HAND).OnActionClient(actionType);
        }
    }
}
