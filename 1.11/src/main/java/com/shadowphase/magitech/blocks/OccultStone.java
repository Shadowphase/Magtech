package com.shadowphase.magitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class OccultStone extends Block {

    private GatewayStabilizer master = null;
    private int multiPos = -1;

    public OccultStone(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        Block block = worldIn.getBlockState(new BlockPos(pos.getX() - 2, pos.getY() + 1, pos.getZ() - 2)).getBlock();
        if (block instanceof GatewayStabilizer) {
            GatewayStabilizer stabalizer = (GatewayStabilizer) block;
            master = stabalizer;
            multiPos = 0;
            master.setMultiPos(multiPos);
        }
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (master != null) {
            master.removeMultiPos(multiPos);
        }
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (master != null) {
            master.removeMultiPos(multiPos);
        }
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }
}
