package pzi.md_je.particles;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.loading.ParticleTex;

@SideOnly(Side.CLIENT)
public class HitDebris extends Particle 
{

    public HitDebris(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, 
                          double xSpeedIn, double ySpeedIn, double zSpeedIn, int subtype) 
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        
        float speedmult = 0.6f;
        float scale = 1.2f;
        if (subtype == 1) 
        {
            speedmult = 1.0f; scale = 0.4f;
            this.setParticleTexture(ParticleTex.tas2);
        }
        else
        {
            this.setParticleTexture(ParticleTex.tas1);
        }
        this.motionX = xSpeedIn * speedmult;
        this.motionY = ySpeedIn * speedmult;
        this.motionZ = zSpeedIn * speedmult;
        this.particleMaxAge = 20; 
        this.particleScale = scale;
        this.particleGravity = 1.5f;
    }
    @Override
	public int getFXLayer() 
    {
		return 1;
	}
    @Override 
    public void onUpdate()
    {
        super.onUpdate();
        this.particleScale *= 0.97d;
    }

}
