package pzi.md_je.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import java.util.List;
import java.util.ArrayList;

public class ModBlocks
{
    @GameRegistry.ObjectHolder("md_je:testblock")
    public static TestBlock testblock;

    public static List<MDBlock> All_Blocks = new ArrayList<>();
}

