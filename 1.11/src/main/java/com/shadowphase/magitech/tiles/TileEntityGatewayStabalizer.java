package com.shadowphase.magitech.tiles;

import com.shadowphase.magitech.blocks.GatewayStabilizer;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGatewayStabalizer extends TileEntity implements ITickable {

    private static final String MULTIBLOCK = "multiblock";
    private static final String MULTIBLOCKS = "multiblocks";

    private boolean multiblock = false;
    private byte[] multiblocks = new byte[GatewayStabilizer.MULTIBLOCK_LOCATIONS.length];

    public TileEntityGatewayStabalizer() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.multiblock = compound.getBoolean(MULTIBLOCK);
        this.multiblocks = compound.getByteArray(MULTIBLOCKS);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean(MULTIBLOCK, this.multiblock);
        compound.setByteArray(MULTIBLOCKS, this.multiblocks);
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
    public void tick() {

    }

    public boolean isMultiblock() {
        return this.multiblock;
    }

    public void setMultiblock(int pos, int val) {
        this.multiblocks[pos] = (byte) val;
        checkCompelete();
    }

    private boolean checkCompelete() {
        for (int i = 0; i < multiblocks.length; ++i) {
            if (multiblocks[i] != 1) {
                multiblock = false;
                break;
            }
            multiblock = true;
        }
        return multiblock;
    }
}
