package pzi.md_je.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pzi.md_je.block.ModBlocks;

public class MDCreativeTab extends CreativeTabs
{
    public MDCreativeTab(String name)
    {
        super(CreativeTabs.getNextID(), name);
    }

    @Override
	public ItemStack createIcon() {
		return new ItemStack(Items.IRON_PICKAXE);
	}
}