package com.shadowphase.magitech.tiles;

import com.shadowphase.magitech.blocks.PortalCore;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityPortalCore extends TileEntity implements ITickable {

    private static final int MAX_TIME = 100;
    private static final String MULTIBLOCK = "multiblock";

    private int timer = 0;
    private int multiblock = 0;

    public TileEntityPortalCore() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.multiblock = compound.getInteger(MULTIBLOCK);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(MULTIBLOCK, this.multiblock);
        return super.writeToNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metaData = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metaData, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            ++timer;
            timer %= MAX_TIME;
            if (timer == 0) {
                checkCompelete();
            }
        }
    }

    public int isMultiblock() {
        return this.multiblock;
    }

    private void checkCompelete() {
        Vec3d[] loc = PortalCore.MULTIBLOCK_LOCATIONS;
        Object[] blocks = PortalCore.PORTAL_BLOCKS;
        int[] tierSizes = PortalCore.TIER_SIZES;
        boolean exit = false;
        int blockCount = 0;
        multiblock = 0;
        for (int j = 1; j <= tierSizes.length; ++j) {
            for (int i = 0; i < tierSizes[j - 1]; ++i, ++blockCount) {
                BlockPos blockPos = new BlockPos(pos.getX() + loc[blockCount].xCoord,
                        pos.getY() + loc[blockCount].yCoord, pos.getZ() + loc[blockCount].zCoord);
                Block block = getWorld().getBlockState(blockPos).getBlock();
                if (!block.getClass().isInstance(blocks[blockCount])) {
                    exit = true;
                    break;
                }
            }
            if (exit) {
                break;
            } else {
                multiblock = j;
            }
        }
    }
}
