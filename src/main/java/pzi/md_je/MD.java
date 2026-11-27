package pzi.md_je;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pzi.md_je.capability.CapabilityHandler;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.IMorphCap;
import pzi.md_je.capability.MorphCap;
import pzi.md_je.capability.MorphCapStorage;
import pzi.md_je.commands.SetMorphCommand;
import pzi.md_je.commands.SetOilCommand;
import pzi.md_je.commands.SetTempCommand;
import pzi.md_je.events.LogInHandler;
import pzi.md_je.events.PlayerTickHandler;
import pzi.md_je.gui.MDCreativeTab;
import pzi.md_je.loading.ModItems;
import pzi.md_je.loading.ServerProxy;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.rendering.AnimLoader;
import pzi.md_je.world.WorldGenHandler;

import java.util.Random;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = MD.MODID, name = MD.MODNAME, version = MD.MODVERSION, dependencies = "required-after:forge@[11.16.0.1865,)", useMetadata = true)
public class MD {

    public static final String MODID = "md_je";
    public static final String MODNAME = "Murder Drones: Java Edition";
    public static final String MODVERSION= "0.1.1";

    public static CreativeTabs tab = new MDCreativeTab(MODID);

    @SidedProxy(clientSide = "pzi.md_je.loading.ClientProxy", serverSide = "pzi.md_je.loading.ServerProxy")
    public static ServerProxy proxy;

    @Mod.Instance(MODID)
    public static MD instance;

    public static Logger logger;

    public static Random random;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        random = new Random();
        proxy.preInit(event);       
        GameRegistry.registerWorldGenerator(new WorldGenHandler(), 0);
        ModItems.register();
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RegisterCapabilities();
        MinecraftForge.EVENT_BUS.register(new LogInHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
        proxy.init(event);
    }

    private static void RegisterCapabilities()
    {
        CapabilityManager.INSTANCE.register(IMorphCap.class, new MorphCapStorage(), MorphCap::new);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        logger.info("Registered capabilities");
    } 

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // Register server-side commands
        event.registerServerCommand(new SetMorphCommand());
        event.registerServerCommand(new SetTempCommand());
        event.registerServerCommand(new SetOilCommand());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) 
    {
        proxy.postInit(e);
        PacketHandler.registerPackets();
    }
}