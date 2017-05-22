package com.shadowphase.magitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class GatewayStabilizer extends Block {
    private boolean multiblock = false;
    private final boolean[] multiblocks = new boolean[] { false };

    public GatewayStabilizer(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        System.out.println("on activate");
        if (!worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString("Multiblock completed: " + multiblock));
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        Block block1 = worldIn.getBlockState(new BlockPos(pos.getX() + 2, pos.getY() - 1, pos.getZ() + 2)).getBlock();
        System.out.println("reg name: " + block1.getRegistryName());
        System.out.println("unloc name: " + block1.getUnlocalizedName());
        if (block1 instanceof OccultStone) {
            System.out.println("Huzzah");
            multiblocks[0] = true;
            checkCompelete();
        }
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        multiblock = false;
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        multiblock = false;
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    public void setMultiPos(int pos) {
        multiblocks[pos] = true;
        checkCompelete();
    }

    public void removeMultiPos(int pos) {
        multiblocks[pos] = false;
        checkCompelete();
    }

    private void checkCompelete() {
        for (int i = 0; i < multiblocks.length; ++i) {
            if (!multiblocks[i]) {
                multiblock = false;
                break;
            }
            multiblock = true;
        }
        if (multiblock) {
            System.out.println("Mission complete!");
        }
    }
}
