package pzi.md_je.loading;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.block.ModBlocks;


@EventBusSubscriber(value = Side.CLIENT, modid = MD.MODID)
public class ModelLoading
{
    @SubscribeEvent
    public static void LoadModels(ModelRegistryEvent event)
    {
        for (int i = 0; i < ModBlocks.All_Blocks.size(); i++)
        {
            ModBlocks.All_Blocks.get(i).registerModel();
        }

    }
    @SideOnly (Side.CLIENT)
    private static void registerItemModel(Item item) 
    {
        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
} 