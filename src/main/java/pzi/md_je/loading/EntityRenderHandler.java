package pzi.md_je.loading;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.EntityDDMorph;
import pzi.md_je.entities.DD.RenderEntityDD;

@Mod.EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public class EntityRenderHandler 
{
    public static RenderEntityDD DD;

    public static void registerRenderers() 
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityDD.class, RenderEntityDD::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDDMorph.class, RenderEntityDD::new);
    }
}
