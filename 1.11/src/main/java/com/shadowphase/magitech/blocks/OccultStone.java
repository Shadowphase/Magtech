package com.shadowphase.magitech.blocks;

import com.shadowphase.magitech.tiles.TileEntityGatewayStabalizer;
import com.shadowphase.magitech.tiles.TileEntityOccultStone;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class OccultStone extends Block implements ITileEntityProvider {

    public OccultStone(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOccultStone();
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityOccultStone) {
                ((TileEntityOccultStone) tileEntity).updateMaster();
            }
        }
        super.onBlockExploded(world, pos, explosion);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityOccultStone) {
                ((TileEntityOccultStone) tileEntity).updateMaster();
            }
        }
    }

    public void setMaster(World world, BlockPos pos, TileEntityGatewayStabalizer master) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityOccultStone) {
            ((TileEntityOccultStone) tileEntity).setMaster(master);
        }
    }
}
