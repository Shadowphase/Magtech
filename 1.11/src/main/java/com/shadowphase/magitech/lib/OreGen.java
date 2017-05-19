package com.shadowphase.magitech.lib;

public class OreGen {
    private String blockName;
    private int oreNumber;
    private int chanceToSpawn;
    private int minHeight;
    private int maxHeight;

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public int getOreNumber() {
        return oreNumber;
    }

    public void setOreNumber(int oreNumber) {
        this.oreNumber = oreNumber;
    }

    public int getChanceToSpawn() {
        return chanceToSpawn;
    }

    public void setChanceToSpawn(int chanceToSpawn) {
        this.chanceToSpawn = chanceToSpawn;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
