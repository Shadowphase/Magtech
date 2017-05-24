package com.shadowphase.magitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class GatewayStabilizer extends Block {

    public GatewayStabilizer(SoundType soundType, Material materialIn) {
        super(materialIn);
        setSoundType(soundType);
    }
}
