package pzi.md_je.generic.weapons;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import pzi.md_je.MD;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.anim.EntityAnimator;
import pzi.md_je.capability.IMorph;
import pzi.md_je.generic.MouseHandler;
import pzi.md_je.gui.PrettyGUI;

public abstract class InnateWeapon<T extends IMorph, R extends AnimModelBase> 
{
    public T morph;
    protected boolean rightClickHeld;
    protected boolean leftClickHeld;
    protected boolean selected;
    public InnateWeapon(T o)
    {
        morph = o;
    }

    public abstract String GetId();
    public abstract int GetNumericId();

    // Client-Side
    public void OnLeftClick() {} 
    public void OnRightClick() {}
    public void OnLeftClickHold() {}
    public void OnRightClickHold() {}

    public void OnRenderPre(R model, float swing) {}
    public void OnRenderPost() {}
    public void OnHandRenderPre(R model, float swing) {}
    public void OnHandRenderPost() {}

    public void OnSelect() {selected = true;}
    public void OnDeselect() {selected = false;}


    public void Update() 
    {
        if (!selected) return;
        if (morph.GetOwner().world.isRemote && morph.GetOwner() == Minecraft.getMinecraft().player)
        {
            if (MouseHandler.lmbHeld) OnLeftClickHold();
            if (MouseHandler.rmbHeld) OnRightClickHold();
        }
    }

    // called on server when a packet is received
    public void OnActionServer(int action) {}
    // called on other clients when they receive a pakcet from server
    public void OnActionClient(int action) {}

    public void DrawGUI(PrettyGUI parent) {}
    public ResourceLocation CrosshairTex() {return null;} // null if not overriding
    public int CrosshairSize() {return 0;} // assume texture is square

    protected EntityAnimator GetAnimator() {return morph.GetAnimator();}
    protected EntityAnimator GetHandAnimator() {return morph.GetHandAnimator();}

    public abstract NBTTagCompound ToNBT();
    public abstract void FromNBT(NBTTagCompound tag);
}
