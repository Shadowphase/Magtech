package com.shadowphase.magitech.blocks;

import java.util.Random;

import com.shadowphase.magitech.Main;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class ReconditeOre extends Block {
    private String dropItem = "enigma_gem";

    public ReconditeOre(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Main.items.get(dropItem).getItem();
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(2) + 1;
    }
}
