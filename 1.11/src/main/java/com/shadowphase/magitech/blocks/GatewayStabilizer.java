package com.shadowphase.magitech.blocks;

import com.shadowphase.magitech.tiles.TileEntityGatewayStabalizer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class GatewayStabilizer extends Block implements ITileEntityProvider {

    public static final Vec3d[] MULTIBLOCK_LOCATIONS = new Vec3d[1];

    public GatewayStabilizer(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
        MULTIBLOCK_LOCATIONS[0] = new Vec3d(2, -1, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGatewayStabalizer();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityGatewayStabalizer) {
                TileEntityGatewayStabalizer stabilizer = (TileEntityGatewayStabalizer) tileEntity;
                if (!stabilizer.isMultiblock()) {
                    ItemStack item = playerIn.getHeldItem(hand);
                    if (item != null && item.getItem() == Items.ENDER_EYE) {
                        for (int i = 0; i < MULTIBLOCK_LOCATIONS.length; ++i) {
                            BlockPos blockPos = new BlockPos(pos.getX() + MULTIBLOCK_LOCATIONS[i].xCoord,
                                    pos.getY() + MULTIBLOCK_LOCATIONS[i].yCoord,
                                    pos.getZ() + MULTIBLOCK_LOCATIONS[i].zCoord);
                            Block block = worldIn.getBlockState(blockPos).getBlock();
                            if (block instanceof OccultStone) {
                                stabilizer.setMultiblock(i, 1);
                                ((OccultStone) block).setMaster(worldIn, blockPos, stabilizer);
                            }
                        }
                        if (stabilizer.isMultiblock()) {
                            item.setCount(item.getCount() - 1);
                        }
                    }
                }
                playerIn.sendMessage(new TextComponentString("Multiblock completed: " + stabilizer.isMultiblock()));
            }
        }
        return true;
    }
}
