package com.shadowphase.magitech.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.lib.Constants;
import com.shadowphase.magitech.lib.ModItem;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static void init() {
        InputStream readIn = null;
        Reader reader = null;
        try {
            final ClassLoader loader = ModItems.class.getClassLoader();
            readIn = loader.getResourceAsStream(Constants.ITEMS_JSON);
            reader = new InputStreamReader(readIn, "UTF-8");
            final ModItem[] items = new Gson().fromJson(reader, ModItem[].class);
            for (int i = 0; i < items.length; ++i) {
                final ModItem modItem = items[i];
                final String name = modItem.getName();
                final String className = modItem.getClassName();
                final Class<?> c = Class.forName(className);
                final Item item = (Item) c.newInstance();
                register(item, name);
                modItem.setItem(item);
                Main.items.put(name, modItem);
            }
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException | IllegalAccessException e) {
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

    public static void initRecipes() {
        for (final Entry<String, ModItem> itemEntry : Main.items.entrySet()) {
            final ModItem item = itemEntry.getValue();
            item.addRecipes();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(final ItemModelMesher mesher) {
        for (final Entry<String, ModItem> itemEntry : Main.items.entrySet()) {
            final ModItem item = itemEntry.getValue();
            registerModel(item.getItem(), item.getName(), mesher);
        }
    }

    private static <T extends Item> void register(final T item, final String name) {
        item.setRegistryName(new ResourceLocation(Main.MODID, name));
        GameRegistry.register(item);
        item.setCreativeTab(Main.itemTab);
        item.setUnlocalizedName(Main.RESOURCE_PREFIX + name);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(final Item item, final String name, final ItemModelMesher mesher) {
        final ModelResourceLocation model = new ModelResourceLocation(Main.RESOURCE_PREFIX + name, "inventory");
        ModelLoader.registerItemVariants(item, model);
        mesher.register(item, 0, model);
    }
}
