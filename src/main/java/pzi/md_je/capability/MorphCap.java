package pzi.md_je.capability;

import net.minecraft.entity.player.EntityPlayer;
import pzi.md_je.MD;
import pzi.md_je.player.Morphs;

public class MorphCap implements IMorphCap
{
    protected EntityPlayer owner;
    public MorphCap(){}
    public MorphCap(EntityPlayer o)
    {
        owner = o;
    }
    protected IMorph Morph;
    public IMorph GetMorph()
    {
        return Morph;
    }
    public void SetMorph(Morphs morph)
    {
        if (Morph != null && Morph.GetMorph() == morph) return;
        if (Morph != null) Morph.OnRemove();
        if (morph == Morphs.DD)
        {
            Morph = new DDMorph(owner);
        }
        else if (morph == Morphs.None)
        {
            Morph = null;
        }
    }
}
