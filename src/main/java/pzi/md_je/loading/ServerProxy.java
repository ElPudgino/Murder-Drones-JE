package pzi.md_je.loading;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.MD;
import pzi.md_je.block.*;
import net.minecraft.item.Item;


@Mod.EventBusSubscriber(modid = MD.MODID)
public class ServerProxy
{
    public boolean isLocalPlayer(net.minecraft.entity.player.EntityPlayer player) { return false; }

    public void preInit(FMLPreInitializationEvent e) {}

    public void init(FMLInitializationEvent e) {}

    public void postInit(FMLPostInitializationEvent e) {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(new TestBlock());

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(ModBlocks.testblock).setRegistryName(ModBlocks.testblock.getRegistryName()));

    }

    
}