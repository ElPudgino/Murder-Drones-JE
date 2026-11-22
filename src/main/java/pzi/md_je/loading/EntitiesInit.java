package pzi.md_je.loading;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import pzi.md_je.MD;
import pzi.md_je.entities.DD.EntityDD;


@Mod.EventBusSubscriber(modid = MD.MODID)
public class EntitiesInit 
{
    private static int entityID = 0;
    
    @SubscribeEvent
    public static void RegisterEntities(RegistryEvent.Register<EntityEntry> e) {
        final EntityEntry[] entityEntries = {
            regEntity_egg("md_dd", EntityDD.class, 60, 1, true, 0xa1d16b, 0xc0bc87)
        };
        e.getRegistry().registerAll(entityEntries);
        RegisterSpawns();
    }

    private static EntityEntry regEntity_egg(String name, @Nonnull Class<? extends Entity> entity, int trackingRange, int trackerUpdate, boolean sendVelocity, int color1, int color2) {
        return EntityEntryBuilder.create().entity(entity).id(new ResourceLocation(MD.MODID, name), entityID++).name(name).egg(color1, color2).tracker(trackingRange,trackerUpdate, sendVelocity).build();
    }
    private static EntityEntry regEntity_no_egg(String name, @Nonnull Class<? extends Entity> entity, int trackingRange, int trackerUpdate, boolean sendVelocity) {
        return EntityEntryBuilder.create().entity(entity).id(new ResourceLocation(MD.MODID, name), entityID++).name(name).tracker(trackingRange,trackerUpdate, sendVelocity).build();
    }

    /**
	 * Bridge Method for simpler spawning registry
	 * 
	 * @param weight        Spawn rate
	 * @param entityclassIn Entity
	 * @param groupCountMin Minimum amount (always 1)
	 * @param groupCountMax Maximum amount (deprecated due to chunk limits)
	 * @param biome         Biome
	 */
	public static void AddSpawn(int weight, Class<? extends EntityLiving> entityclassIn, Predicate<Biome> predicate, int groupCountMin, int groupCountMax, Biome biome) {
		if (weight > 0 && predicate.test(biome)) {
			biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(entityclassIn, weight, groupCountMin, groupCountMax));
		}
	}

    private static void RegisterSpawns()
    {
        for (Biome biome : Biome.REGISTRY) 
        {
            
        }
    }

}
