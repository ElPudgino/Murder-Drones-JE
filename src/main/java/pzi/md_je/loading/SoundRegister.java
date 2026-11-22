package pzi.md_je.loading;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pzi.md_je.MD;

@Mod.EventBusSubscriber(modid=MD.MODID)
public class SoundRegister 
{
    public static final SoundEvent shot_event = createSoundEvent("gunshot");
    public static final SoundEvent slash_event = createSoundEvent("slash");

    private static SoundEvent createSoundEvent(String name) 
    {
        ResourceLocation location = new ResourceLocation(MD.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        return event;
    }
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) 
    {
        event.getRegistry().register(shot_event);
        event.getRegistry().register(slash_event);
    }
}
