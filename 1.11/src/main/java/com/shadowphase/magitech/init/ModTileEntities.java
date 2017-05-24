package com.shadowphase.magitech.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.lib.Constants;
import com.shadowphase.magitech.lib.ModTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> void init() {
        InputStream readIn = null;
        Reader reader = null;
        try {
            ClassLoader loader = ModBlocks.class.getClassLoader();
            readIn = loader.getResourceAsStream(Constants.TILES_JSON);
            reader = new InputStreamReader(readIn, "UTF-8");
            ModTileEntity[] tiles = new Gson().fromJson(reader, ModTileEntity[].class);
            for (int i = 0; i < tiles.length; ++i) {
                final ModTileEntity tileEntity = tiles[i];
                final String name = tileEntity.getName();
                final String className = tileEntity.getClassName();

                final Class<T> tileEntityClass = (Class<T>) Class.forName(className);
                register(tileEntityClass, name);
            }
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final UnsupportedEncodingException e) {
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

    private static <T extends TileEntity> void register(final Class<T> tileEntityClass, final String name) {
        GameRegistry.registerTileEntity(tileEntityClass, Main.RESOURCE_PREFIX + name);
    }
}
