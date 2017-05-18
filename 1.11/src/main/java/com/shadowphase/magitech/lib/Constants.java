package com.shadowphase.magitech.lib;

import com.shadowphase.magitech.Main;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Constants {

    public static final String ITEMS_JSON = "assets/magitech/items/items.json";
    public static final String BLOCKS_JSON = "assets/magitech/blocks/blocks.json";
    public static final String ITEM = "Items";
    public static final String BLOCK = "Blocks";

    public static final ToolMaterial TOOL_MAT = EnumHelper.addToolMaterial(Main.RESOURCE_PREFIX + ".toolMat", 4, 2048,
            10.0f, 4.0f, 16);

    /**** BLOCKS ****/
    public static final String BLOCK_NAME = "dragon_block";

    public static enum RecipeType {
        SMELT, SHAPED, SHAPELESS, SHAPEDORE, RECIPE
    }
}
