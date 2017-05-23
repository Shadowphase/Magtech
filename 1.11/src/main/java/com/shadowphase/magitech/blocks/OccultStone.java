package com.shadowphase.magitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class OccultStone extends Block {

    public OccultStone(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
    }
}
