package com.shadowphase.magitech.tiles;

import com.shadowphase.magitech.blocks.GatewayStabilizer;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityGatewayStabalizer extends TileEntity implements ITickable {

    private int timer = 0;
    private static final String MULTIBLOCK = "multiblock";

    private boolean multiblock = false;

    public TileEntityGatewayStabalizer() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.multiblock = compound.getBoolean(MULTIBLOCK);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean(MULTIBLOCK, this.multiblock);
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
            timer++;
            timer %= 100;
            if (timer == 0) {
                checkCompelete();
            }
        }
    }

    public boolean isMultiblock() {
        return this.multiblock;
    }

    private void checkCompelete() {
        Vec3d[] loc = GatewayStabilizer.MULTIBLOCK_LOCATIONS;
        Object[] blocks = GatewayStabilizer.PORTAL_BLOCKS;
        for (int i = 0; i < GatewayStabilizer.structSize; ++i) {
            BlockPos blockPos = new BlockPos(pos.getX() + loc[i].xCoord, pos.getY() + loc[i].yCoord,
                    pos.getZ() + loc[i].zCoord);
            Block block = getWorld().getBlockState(blockPos).getBlock();
            if (!block.getClass().isInstance(blocks[i])) {
                multiblock = false;
                break;
            }
            multiblock = true;
        }
    }
}
