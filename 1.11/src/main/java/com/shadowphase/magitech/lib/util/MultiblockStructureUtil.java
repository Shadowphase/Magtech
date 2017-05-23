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

    private int size = 0;
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
            int[][] inPos = structure.getPositions();
            String[] inBlocks = structure.getBlocks();

            positions = new Vec3d[getSize()];
            blocks = new Object[getSize()];

            for (int i = 0; i < getSize(); ++i) {
                int[] pos = inPos[i];
                positions[i] = new Vec3d(pos[0], pos[1], pos[2]);

                String block = inBlocks[i];
                if (Main.items.containsKey(block)) {
                    blocks[i] = Main.items.get(block).getItem();
                } else if (Main.blocks.containsKey(block)) {
                    blocks[i] = Main.blocks.get(block).getBlock();
                } else if (block.startsWith(Constants.ITEM)) {
                    final int charIndex = block.indexOf('.');
                    final String itemName = block.substring(charIndex + 1);
                    blocks[i] = NameConverter.getField(Items.class, itemName);
                } else if (block.startsWith(Constants.BLOCK)) {
                    final int charIndex = block.indexOf('.');
                    final String blockName = block.substring(charIndex + 1);
                    blocks[i] = NameConverter.getField(Blocks.class, blockName);
                } else {

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

    public Vec3d[] getPositions() {
        return positions;
    }

    public Object[] getBlocks() {
        return blocks;
    }

}
