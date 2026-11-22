package pzi.md_je.loading;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.rendering.ShaderUtils;

@Mod.EventBusSubscriber(modid = MD.MODID, value = Side.CLIENT)
public class ClientProxy extends ServerProxy
{
    public static KeyBinding keyFly;
    public static KeyBinding keyStopFly;
    public static KeyBinding keyUnfoldWings;
    public static KeyBinding keyFoldWings;
    public static KeyBinding keyTakeOff;
    public static KeyBinding keyCombatMode;
    public static KeyBinding keyNextWeapon;
    public static KeyBinding keySpecial;

    @Override
    public boolean isLocalPlayer(net.minecraft.entity.player.EntityPlayer player)
    {
        return player.world.isRemote && player == Minecraft.getMinecraft().player;
    }

    public void initKeybinds()
    {
        

        keyFly = createKeyBind("key.fly.desc", Keyboard.KEY_SPACE, "key.md_je.category");
        keyStopFly = createKeyBind("key.fly_stop.desc", Keyboard.KEY_B, "key.md_je.category");
        keyUnfoldWings = createKeyBind("key.unfold_wings.desc", Keyboard.KEY_U, "key.md_je.category");
        keyFoldWings = createKeyBind("key.fold_wings.desc", Keyboard.KEY_M, "key.md_je.category");
        keyTakeOff = createKeyBind("key.takeoff.desc", Keyboard.KEY_Z, "key.md_je.category");
        keyCombatMode = createKeyBind("key.combat_mode.desc", Keyboard.KEY_R, "key.md_je.category");
        keyNextWeapon = createKeyBind("key.next_weapon.desc", Keyboard.KEY_V, "key.md_je.category");
        keySpecial = createKeyBind("key.special.desc", Keyboard.KEY_G, "key.md_je.category");
    }

    private static KeyBinding createKeyBind(String name, int key, String cat)
    {
        KeyBinding k = new KeyBinding(name, key, cat);
        ClientRegistry.registerKeyBinding(k);
        return k;
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) 
    {
        EntityRenderHandler.registerRenderers();
        ShaderUtils.initShaders();
        OBJLoader.INSTANCE.addDomain(MD.MODID);
        initKeybinds();
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        ModelEntityDD.LoadAnims();
    }
}