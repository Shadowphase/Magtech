package com.shadowphase.magitech.blocks;

import com.shadowphase.magitech.lib.Constants;
import com.shadowphase.magitech.lib.util.MultiblockStructureUtil;
import com.shadowphase.magitech.tiles.TileEntityPortalCore;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class PortalCore extends Block implements ITileEntityProvider {

    public static int structSize = 0;
    public static int[] TIER_SIZES;
    public static Vec3d[] MULTIBLOCK_LOCATIONS;
    public static Object[] PORTAL_BLOCKS;

    public PortalCore(final SoundType soundType, final Material mat) {
        super(mat);
        setSoundType(soundType);
        MultiblockStructureUtil structUtil = new MultiblockStructureUtil(Constants.PORTAL_JSON);
        structSize = structUtil.getSize();
        TIER_SIZES = structUtil.getTierSizes();
        MULTIBLOCK_LOCATIONS = structUtil.getPositions();
        PORTAL_BLOCKS = structUtil.getBlocks();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPortalCore();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityPortalCore) {
                TileEntityPortalCore stabilizer = (TileEntityPortalCore) tileEntity;
                playerIn.sendMessage(new TextComponentString("Portal Tier: " + stabilizer.isMultiblock()));
            }
        }
        return true;
    }
}
