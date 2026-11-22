package pzi.md_je.block;

import net.minecraft.block.material.Material;
import pzi.md_je.*;

public class TestBlock extends MDBlock
{
    public TestBlock()
    {
        super(Material.PLANTS, "testblock");
        ModBlocks.testblock = this;
    }

    
}