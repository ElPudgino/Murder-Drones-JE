package pzi.md_je.generic.weapons;

import net.minecraft.nbt.NBTTagCompound;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_server.SUseWeaponPacket;

public class DD_Hand extends InnateWeapon<DDMorph, ModelEntityDD>
{
    @Override 
    public void OnHandRenderPre(ModelEntityDD model)
    {
        model.hand_normal.isHidden = false;
        GetHandAnimator().ContinueAnim(ModelEntityDD.claws_idle, 1f, 1f);
    }

    @Override 
    public void OnRenderPre(ModelEntityDD model)
    {
        model.hand_normal.isHidden = false;
    }

    @Override
    public void OnLeftClick()
    {
        PacketHandler.INSTANCE.sendToServer(new SUseWeaponPacket(1, GetNumericId()));
        GetAnimator().StartAnim(ModelEntityDD.hit, 1f, 1f);
        GetHandAnimator().StartAnim(ModelEntityDD.claws_swing, 1.8f, 1f);
    }

    @Override
    public void OnActionClient(int action)
    {
        GetAnimator().StartAnim(ModelEntityDD.hit, 1f, 1f);
    }

    public DD_Hand(DDMorph o) 
    {
        super(o);
    }

    @Override
    public String GetId() 
    {
        return "Empty";
    }
    @Override 
    public int GetNumericId() {return 0;}

    @Override
    public NBTTagCompound ToNBT() 
    {
        return new NBTTagCompound();
    }

    @Override
    public void FromNBT(NBTTagCompound tag) {}
    
}
