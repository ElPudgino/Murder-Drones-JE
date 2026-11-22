package pzi.md_je.capability;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.entities.HandRender;
import pzi.md_je.gui.PrettyGUI;
import pzi.md_je.player.Morphs;

public interface IMorph
{
    public void RenderAt(double x, double y, double z, float partial);
    public PrettyGUI GetGui();
    public float GetEyeHeight();
    public Vec2f GetSize();
    public AxisAlignedBB GetRenderBB();
    public void RenderHands(float swing);
    public boolean OnAttackEntity(AttackEntityEvent event); // return true to cancel; called on both sides
    public boolean OnLeftClickBlock(PlayerInteractEvent.LeftClickBlock event); // return true to cancel; called on both sides
    public void OnLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event); // called on client
    public void OnLeftClick();
    public void OnRightClick();
    public void OnUpdate(Side side);
    public void OnRemove();
    public void OnHurt(LivingHurtEvent e);
    public void OnAttacked(LivingAttackEvent e);
    public void OnTookDamage(LivingDamageEvent e);
    public void OnRespawned();
    public boolean HideHeldItem(EnumHand hand);
    public Morphs GetMorph();
    public EntityPlayer GetOwner();
    public EntityAnimator GetAnimator();
    public EntityAnimator GetHandAnimator();
    public NBTTagCompound ToNBT();
    public void FromNBT(NBTTagCompound tag);
    public NBTTagCompound ToSyncNBT(boolean full); // this tag is sent to other players for sync
    public void FromSyncNBT(NBTTagCompound tag);
    public void RenderTick(float dt);
}