package com.shadowphase.magitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockBlock1 extends Block {

    public BlockBlock1() {
        super(Material.ROCK);
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.ANVIL);
        setHarvestLevel("pickaxe", 1);
    }

    public void addRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(this), " l ", "lwl", " l ", 'l', Blocks.LADDER, 'w',
                new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "dld", "lwl", "dld", 'l', Blocks.LADDER, 'w',
                Blocks.WOOL, 'd', "dyeBlack"));
    }
}
