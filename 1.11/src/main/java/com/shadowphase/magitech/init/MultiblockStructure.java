package com.shadowphase.magitech.init;

public class MultiblockStructure {
    private int[] tierSizes;
    private int[][] positions;
    private String[] blocks;

    public int[] getTierSizes() {
        return tierSizes;
    }

    public void setTierSizes(int[] tierSizes) {
        this.tierSizes = tierSizes;
    }

    public int[][] getPositions() {
        return positions;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public String[] getBlocks() {
        return blocks;
    }

    public void setBlocks(String[] blocks) {
        this.blocks = blocks;
    }

    public int getSize() {
        int size = 0;
        for (int i = 0; i < tierSizes.length; ++i) {
            size += tierSizes[i];
        }
        return size;
    }
}
