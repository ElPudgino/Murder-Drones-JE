package pzi.md_je.entities.DD;


import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.generic.HeatManager;

public class EntityDDMorph extends EntityDD
{
    public DDMorph morph;
    public EntityDDMorph(World worldIn) 
    {
        super(worldIn);
    }  

    @Override 
    public StatsDD getStats()
    {
        return morph.statsDD;
    }
    @Override 
    public boolean isDashing()
    {
        return morph.isDashing();
    }

    @Override 
    public boolean isAiming(EnumHand hand)
    {
        return morph.isAiming(hand);
    }
    @Override 
    public boolean isSneaking()
    {
        return morph.owner.isSneaking() || morph.owner.height == morph.GetCrouchSize().y;
    }
    @Override 
    public float GetCharge()
    {
        return morph.GetCharge();
    }
    @Override 
    public float AddCharge(float delta)
    {
        return morph.AddCharge(delta);
    }
    @Override
    public HeatManager GetHeatManager() 
    {
        return morph.GetHeatManager();
    }
    @Override 
    public float GetCapacity()
    {
        return morph.GetCapacity();
    }
    @Override 
    public float GetCurrentOil()
    {
        return morph.GetCurrentOil();
    }
    @Override 
    public float Input(float amount)
    {
        return morph.Input(amount);
    }
    @Override 
    public void SetOil(float val)
    {
        morph.SetOil(val);
    }
    @Override 
    public FlightDD GetFlightController()
    {
        return morph.flightController;
    }
}
