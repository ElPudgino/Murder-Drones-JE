package pzi.md_je.block;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import pzi.md_je.*;
import net.minecraft.block.material.Material;

public class MDBlock extends Block
{ 
    public MDBlock(Material mat, String name)
    {
        super(mat);
        InitBlock(name); 
        ModBlocks.All_Blocks.add(this);         
    }

    protected void InitBlock(String name)
    {
        setRegistryName(name);    
        setTranslationKey(name);
        this.setCreativeTab(MD.tab);
    }

    public void registerModel() 
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));    
    }
}