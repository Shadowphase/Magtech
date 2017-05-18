package com.shadowphase.magitech.lib;

import java.lang.reflect.Field;

import com.shadowphase.magitech.Main;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModBlock {
    private Object block;
    private String name;
    private String className;
    private String material;
    private String hardness;
    private String resistance;
    private String sound;
    private String harvestItem;
    private String harvestLevel;
    private Constants.RecipeType recipeType;
    private String[] recipe;

    public Object getBlock() {
        return block;
    }

    public void setBlock(final Object item) {
        this.block = item;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(final String material) {
        this.material = material;
    }

    public String getHardness() {
        return hardness;
    }

    public void setHardness(final String hardness) {
        this.hardness = hardness;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(final String resistance) {
        this.resistance = resistance;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(final String sound) {
        this.sound = sound;
    }

    public String getHarvestItem() {
        return harvestItem;
    }

    public void setHarvestItem(final String harvestItem) {
        this.harvestItem = harvestItem;
    }

    public String getHarvestLevel() {
        return harvestLevel;
    }

    public void setHarvestLevel(final String harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public Constants.RecipeType getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(final Constants.RecipeType recipeType) {
        this.recipeType = recipeType;
    }

    public String[] getRecipe() {
        return recipe;
    }

    public void setRecipe(final String[] recipe) {
        this.recipe = recipe;
    }

    public void addRecipes() {
        switch (recipeType) {
        case SMELT:
            addSmeltingRecipe();
            break;
        case RECIPE:
            addGenericRecipe();
            break;
        default:
            break;
        }
    }

    private void addSmeltingRecipe() {
        String recipeItem = recipe[0];
        try {
            if (Main.items.containsKey(recipeItem)) {
                GameRegistry.addSmelting(new ItemStack((Item) Main.items.get(recipeItem).getItem()),
                        new ItemStack((Block) block), Float.parseFloat(recipe[1]));
            } else if (Main.blocks.containsKey(recipeItem)) {
                GameRegistry.addSmelting(new ItemStack((Block) Main.blocks.get(recipeItem).getBlock()),
                        new ItemStack((Block) block), Float.parseFloat(recipe[1]));
            } else if (recipeItem.startsWith(Constants.ITEM)) {
                final int charIndex = recipeItem.indexOf('.');
                final String recipeItemName = recipeItem.substring(charIndex + 1);
                final Field field = Items.class.getField(recipeItemName);
                GameRegistry.addSmelting((Item) field.get(null), new ItemStack((Block) block),
                        Float.parseFloat(recipe[1]));
            } else if (recipeItem.startsWith(Constants.BLOCK)) {
                final int charIndex = recipeItem.indexOf('.');
                final String recipeItemName = recipeItem.substring(charIndex + 1);
                final Field field = Block.class.getField(recipeItemName);
                GameRegistry.addSmelting((Block) field.get(null), new ItemStack((Block) block),
                        Float.parseFloat(recipe[1]));
            } else {

            }
        } catch (final NoSuchFieldException | SecurityException e) {
            System.out.println("Error for item " + name);
            e.printStackTrace();
        } catch (final IllegalArgumentException | IllegalAccessException e) {
            System.out.println("Error for item " + name);
            e.printStackTrace();
        }
    }

    private void addGenericRecipe() {
        try {
            final Object[] params = new Object[recipe.length];
            params[0] = recipe[0];
            params[1] = recipe[1];
            params[2] = recipe[2];
            for (int i = 3; i < recipe.length; ++i) {
                final String inputStr = recipe[i];
                if (inputStr.length() > 1) {
                    if (Main.items.containsKey(inputStr)) {
                        params[i] = new ItemStack((Item) Main.items.get(inputStr).getItem());
                    } else if (Main.blocks.containsKey(inputStr)) {
                        params[i] = new ItemStack((Block) Main.blocks.get(inputStr).getBlock());
                    } else if (inputStr.startsWith(Constants.ITEM)) {
                        final int charIndex = inputStr.indexOf('.');
                        final String recipeItemName = inputStr.substring(charIndex + 1);
                        final Field field = Items.class.getField(recipeItemName);
                        params[i] = field.get(null);
                    } else if (inputStr.startsWith(Constants.BLOCK)) {
                        final int charIndex = inputStr.indexOf('.');
                        final String recipeItemName = inputStr.substring(charIndex + 1);
                        final Field field = Blocks.class.getField(recipeItemName);
                        params[i] = field.get(null);
                    } else {
                        params[i] = inputStr;
                    }
                } else {
                    params[i] = inputStr.toCharArray()[0];
                }
            }
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((Block) block), params));
        } catch (final NoSuchFieldException | SecurityException e) {
            System.out.println("Error for item " + name);
            e.printStackTrace();
        } catch (final IllegalArgumentException | IllegalAccessException e) {
            System.out.println("Error for item " + name);
            e.printStackTrace();
        }
    }
}
