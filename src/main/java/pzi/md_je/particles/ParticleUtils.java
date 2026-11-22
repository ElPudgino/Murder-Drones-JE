package pzi.md_je.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;

@SideOnly(Side.CLIENT)
public class ParticleUtils 
{

    public static void spawnParticle(World world, Particle p) 
    {
        Minecraft.getMinecraft().effectRenderer.addEffect(p);
    }

}
