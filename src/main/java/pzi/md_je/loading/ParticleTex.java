package pzi.md_je.loading;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MD.MODID)
public class ParticleTex
{
    public static TextureAtlasSprite tas1;
    public static TextureAtlasSprite tas2;
    @SubscribeEvent 
    public static void onTextureStitch(TextureStitchEvent.Pre event) 
    {
        tas1 = event.getMap().registerSprite(ResourcesHelper.GetLocation("particles/hit_generic"));
        tas2 = event.getMap().registerSprite(ResourcesHelper.GetLocation("particles/hit_metal"));
    }
}
