package com.shadowphase.magitech.lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.init.ModBlocks;
import com.shadowphase.magitech.init.MultiblockStructure;
import com.shadowphase.magitech.lib.Constants;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;

public class MultiblockStructureUtil {

    private int size;
    private int[] tierSizes;
    private Vec3d[] positions;
    private Object[] blocks;

    public MultiblockStructureUtil(String file) {
        InputStream readIn = null;
        Reader reader = null;
        try {
            ClassLoader loader = ModBlocks.class.getClassLoader();
            readIn = loader.getResourceAsStream(file);
            reader = new InputStreamReader(readIn, "UTF-8");
            MultiblockStructure structure = new Gson().fromJson(reader, MultiblockStructure.class);

            size = structure.getSize();
            tierSizes = structure.getTierSizes();
            int[][] inPos = structure.getPositions();
            String[] inBlocks = structure.getBlocks();

            positions = new Vec3d[size];
            blocks = new Object[size];

            int blockCount = 0;
            for (int j = 0; j < tierSizes.length; ++j) {
                for (int i = 0; i < tierSizes[j]; ++i, ++blockCount) {
                    int[] pos = inPos[blockCount];
                    positions[blockCount] = new Vec3d(pos[0], pos[1], pos[2]);

                    String block = inBlocks[blockCount];
                    if (Main.items.containsKey(block)) {
                        blocks[blockCount] = Main.items.get(block).getItem();
                    } else if (Main.blocks.containsKey(block)) {
                        blocks[blockCount] = Main.blocks.get(block).getBlock();
                    } else if (block.startsWith(Constants.ITEM)) {
                        final int charIndex = block.indexOf('.');
                        final String itemName = block.substring(charIndex + 1);
                        blocks[blockCount] = NameConverter.getField(Items.class, itemName);
                    } else if (block.startsWith(Constants.BLOCK)) {
                        final int charIndex = block.indexOf('.');
                        final String blockName = block.substring(charIndex + 1);
                        blocks[blockCount] = NameConverter.getField(Blocks.class, blockName);
                    } else {

                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (readIn != null) {
                    readIn.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int[] getTierSizes() {
        return tierSizes;
    }

    public Vec3d[] getPositions() {
        return positions;
    }

    public Object[] getBlocks() {
        return blocks;
    }

}
