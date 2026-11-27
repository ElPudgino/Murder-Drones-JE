package pzi.md_je.generic.weapons;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.entities.HandRender;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.events.HandEvent;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_server.SUseWeaponPacket;

public class DD_Hand extends InnateWeapon<DDMorph, ModelEntityDD>
{
    @Override 
    public void OnHandRenderPre(ModelEntityDD model, float swing)
    {
        model.hand_normal.isHidden = false;
        if (swing > 0.001f) GetHandAnimator().SetAnimProgress(ModelEntityDD.mine, 1f, swing);
        else GetHandAnimator().ContinueAnim(ModelEntityDD.claws_idle, 1f, 1f);
    }

    @Override 
    public void OnRenderPre(ModelEntityDD model, float swing)
    {
        model.hand_normal.isHidden = false;
        MD.logger.info(swing);
        if (swing > 0.001f) GetAnimator().SetAnimProgress(ModelEntityDD.hit, 1f, swing);
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
