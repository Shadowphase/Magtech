package com.shadowphase.magitech.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.lib.Constants;
import com.shadowphase.magitech.lib.ModBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static void init() {
        InputStream readIn = null;
        Reader reader = null;
        try {
            ClassLoader loader = ModBlocks.class.getClassLoader();
            readIn = loader.getResourceAsStream(Constants.BLOCKS_JSON);
            reader = new InputStreamReader(readIn, "UTF-8");
            ModBlock[] blocks = new Gson().fromJson(reader, ModBlock[].class);
            for (int i = 0; i < blocks.length; ++i) {
                final ModBlock modBlock = blocks[i];
                final String name = modBlock.getName();
                final String className = modBlock.getClassName();

                String paramStr = modBlock.getMaterial();
                int charIndex = paramStr.indexOf('.');
                String paramName = paramStr.substring(charIndex + 1);
                System.out.println("material: " + Material.ROCK);
                final Field matField = Material.class.getField(paramName);

                paramStr = modBlock.getSound();
                charIndex = paramStr.indexOf('.');
                paramName = paramStr.substring(charIndex + 1);
                final Field soundField = SoundType.class.getField(paramName);

                final Block block = (Block) Class.forName(className).getConstructor(SoundType.class, Material.class)
                        .newInstance(soundField.get(null), matField.get(null));
                initBlock(modBlock, block);
                register(block, name);
                modBlock.setBlock(block);
                Main.blocks.put(name, modBlock);
            }
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final SecurityException | NoSuchFieldException e) {
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

    public static void initRecipes() {
        for (final Entry<String, ModBlock> itemEntry : Main.blocks.entrySet()) {
            final ModBlock block = itemEntry.getValue();
            block.addRecipes();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(final ItemModelMesher mesher) {
        for (final Entry<String, ModBlock> itemEntry : Main.blocks.entrySet()) {
            final ModBlock block = itemEntry.getValue();
            registerModel(block.getBlock(), block.getName(), mesher);
        }
    }

    private static void initBlock(final ModBlock modBlock, final Block block) {
        try {
            block.setHardness(Float.parseFloat(modBlock.getHardness()));
            block.setResistance(Float.parseFloat(modBlock.getResistance()));
            block.setHarvestLevel(modBlock.getHarvestItem(), Integer.parseInt(modBlock.getHarvestLevel()));
        } catch (final SecurityException e) {
            e.printStackTrace();
        }
    }

    private static <T extends Block> void register(final T block, final String name) {
        final ResourceLocation location = new ResourceLocation(Main.MODID, name);
        block.setRegistryName(location);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), location);
        block.setCreativeTab(Main.itemTab);
        block.setUnlocalizedName(Main.RESOURCE_PREFIX + name);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(final Block block, final String name, final ItemModelMesher mesher) {
        final Item item = Item.getItemFromBlock(block);
        final ModelResourceLocation model = new ModelResourceLocation(Main.RESOURCE_PREFIX + name, "inventory");
        ModelLoader.registerItemVariants(item, model);
        mesher.register(item, 0, model);
    }
}
