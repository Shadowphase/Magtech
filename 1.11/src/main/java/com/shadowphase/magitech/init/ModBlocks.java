package com.shadowphase.magitech.init;

import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.blocks.BlockBlock1;
import com.shadowphase.magitech.lib.Constants;

import net.minecraft.block.Block;
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

    public static BlockBlock1 block;

    public static void init() {
        block = register(new BlockBlock1(), Constants.BLOCK_NAME);
    }

    public static void initRecipes() {
        block.addRecipes();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(ItemModelMesher mesher) {
        registerModel(block, Constants.BLOCK_NAME, mesher);
    }

    protected static <T extends Block> T register(T block, String name) {
        ResourceLocation location = new ResourceLocation(Main.MODID, name);
        block.setRegistryName(location);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), location);
        block.setCreativeTab(Main.itemTab);
        block.setUnlocalizedName(Main.RESOURCE_PREFIX + name);
        return block;
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Block block, String name, ItemModelMesher mesher) {
        Item item = Item.getItemFromBlock(block);
        ModelResourceLocation model = new ModelResourceLocation(Main.RESOURCE_PREFIX + name, "inventory");
        ModelLoader.registerItemVariants(item, model);
        mesher.register(item, 0, model);
    }
}
