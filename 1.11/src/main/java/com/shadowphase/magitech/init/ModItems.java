package com.shadowphase.magitech.init;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.items.ItemItem1;
import com.shadowphase.magitech.items.tools.DragonPick;
import com.shadowphase.magitech.lib.Constants;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemItem1 item;
    public static final ToolMaterial toolMat = EnumHelper.addToolMaterial(Main.RESOURCE_PREFIX + ".toolMat", 4, 2048,
            10.0f, 4.0f, 16);
    public static DragonPick pick;

    public static void init() {
        FileReader file = null;
        try {
            file = new FileReader(Constants.RESOURCE_FOLDER + "assets/magitech/items/items.json");
            Gson gson = new Gson();
            // JsonParser parser = new JsonParser();
            // JsonElement obj = parser.parse(file);
            // if (obj.isJsonObject()) {
            // System.out.println("obj: " + obj);
            // JsonObject jObj = obj.getAsJsonObject();
            // JsonArray arr = jObj.getAsJsonArray("items");
            // if (arr.isJsonArray()) {
            // System.out.println("arr: " + arr);
            // JsonElement elem = arr.get(0);
            // String str = elem.getAsJsonObject().get("name").getAsString();
            // System.out.println("name: " + str);
            // }
            // System.out.println("endif");
            // }
            Response response = gson.fromJson(file, Response.class);
            System.out.println("response: " + response);
            Test[] map = response.getDescriptor();
            System.out.println("map: " + map);
            String name = map[0].getName();
            System.out.println("name: " + name);

            item = register(new ItemItem1(), name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pick = register(new DragonPick(toolMat), Constants.DRAGON_PICK);
    }

    public static void initRecipes() {
        item.addRecipes();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(ItemModelMesher mesher) {
        registerModel(item, Constants.ITEM_NAME, mesher);
        registerModel(pick, Constants.DRAGON_PICK, mesher);
    }

    protected static <T extends Item> T register(T item, String name) {
        item.setRegistryName(new ResourceLocation(Main.MODID, name));
        GameRegistry.register(item);
        item.setCreativeTab(Main.itemTab);
        item.setUnlocalizedName(Main.RESOURCE_PREFIX + name);
        return item;
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, String name, ItemModelMesher mesher) {
        ModelResourceLocation model = new ModelResourceLocation(Main.RESOURCE_PREFIX + name, "inventory");
        ModelLoader.registerItemVariants(item, model);
        mesher.register(item, 0, model);
    }
}

class Response {
    Test[] descriptor;

    Test[] getDescriptor() {
        return descriptor;
    }

    void setDesciptor(Test[] descriptor) {
        this.descriptor = descriptor;
    }
}

class Test {
    String name;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
