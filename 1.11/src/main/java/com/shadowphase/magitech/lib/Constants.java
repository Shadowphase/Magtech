package com.shadowphase.magitech.lib;

import com.shadowphase.magitech.Main;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.util.EnumHelper;

public class Constants {

    public static final boolean DEV_ENV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static final String ITEMS_JSON = "assets/magitech/items/items.json";
    public static final String BLOCKS_JSON = "assets/magitech/blocks/blocks.json";
    public static final String ORE_GENERATION = "assets/magitech/world/oreGeneration.json";
    public static final String FIELD_MAP = "assets/magitech/util/field_map.json";
    public static final String METHOD_MAP = "assets/magitech/util/method_map.json";
    public static final String ITEM = "Items";
    public static final String BLOCK = "Blocks";

    public static final ToolMaterial TOOL_MAT = EnumHelper.addToolMaterial(Main.RESOURCE_PREFIX + ".toolMat", 4, 2048,
            10.0f, 4.0f, 16);

    public static enum RecipeType {
        SMELT, SHAPED, SHAPELESS, RECIPE, NONE
    }
}
