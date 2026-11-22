package pzi.md_je.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import pzi.md_je.MD;

public abstract class StructureGen extends WorldGenerator
{
    protected String structureName;
    protected PlacementSettings settings;
    public StructureGen() {}

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) 
    {
        MinecraftServer serv = worldIn.getMinecraftServer();
        WorldServer ws = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(worldIn.provider.getDimension());
        TemplateManager mng = ws.getStructureTemplateManager();
        Template temp = mng.get(serv, new ResourceLocation(MD.MODID, structureName));
        if (temp != null)
        {
            temp.addBlocksToWorld(worldIn, position, settings);
        }
        return true;
    }
    
    public void attemptGeneration(World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
    {
        ArrayList<Class<?>> biomes = new ArrayList<Class<?>>(Arrays.asList(classes));
        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = getY(world, random, x, z);
        BlockPos bp = new BlockPos(x, y, z);

        Class<?> biome = world.provider.getBiomeForCoords(bp).getClass();
        if (biomes.contains(biome) || world.getWorldType() == WorldType.FLAT)
        {
            if (random.nextInt(chance) == 0)
            {
                generate(world, random, bp);
            }
        }
    }
    protected int getY(World world, Random random, int x, int z)
    {
        return 20;
    }
}
