package com.shadowphase.magitech.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityOccultStone extends TileEntity {

    private static final String M_POS_X = "mXpos";
    private static final String M_POS_Y = "mYpos";
    private static final String M_POS_Z = "mZpos";

    private TileEntityGatewayStabalizer master;

    public TileEntityOccultStone() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        BlockPos masterPos = new BlockPos(compound.getInteger(M_POS_X), compound.getInteger(M_POS_Y),
                compound.getInteger(M_POS_Z));
        World world = getWorld();
        if (world != null) {
            TileEntity tileEntity = world.getTileEntity(masterPos);
            if (tileEntity instanceof TileEntityGatewayStabalizer) {
                this.master = (TileEntityGatewayStabalizer) tileEntity;
            } else {
                compound.removeTag(M_POS_X);
                compound.removeTag(M_POS_Y);
                compound.removeTag(M_POS_Z);
                this.master = null;
            }
        }
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (this.master != null) {
            compound.setInteger(M_POS_X, this.master.getPos().getX());
            compound.setInteger(M_POS_Y, this.master.getPos().getY());
            compound.setInteger(M_POS_Z, this.master.getPos().getZ());
        }
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

    public void setMaster(TileEntityGatewayStabalizer master) {
        this.master = master;
    }

    public void updateMaster() {
        if (master != null) {
            master.setMultiblock(0, 0);
        }
    }
}
