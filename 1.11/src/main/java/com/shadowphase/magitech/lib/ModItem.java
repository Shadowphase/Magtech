package com.shadowphase.magitech.lib;

import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.lib.util.NameConverter;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModItem {
    private Object item;
    private String name;
    private String className;
    private Constants.RecipeType recipeType;
    private String[] recipe;

    public Item getItem() {
        return (Item) item;
    }

    public void setItem(final Object item) {
        this.item = item;
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
        final String recipeItem = recipe[0];
        if (Main.items.containsKey(recipeItem)) {
            GameRegistry.addSmelting(new ItemStack(Main.items.get(recipeItem).getItem()), new ItemStack((Item) item),
                    Float.parseFloat(recipe[1]));
        } else if (recipeItem.startsWith(Constants.ITEM)) {
            final int charIndex = recipeItem.indexOf('.');
            final String recipeItemName = recipeItem.substring(charIndex + 1);
            final Item item = (Item) NameConverter.getField(Items.class, recipeItemName);
            GameRegistry.addSmelting(item, new ItemStack(item), Float.parseFloat(recipe[1]));
        } else if (recipeItem.startsWith(Constants.BLOCK)) {
            final int charIndex = recipeItem.indexOf('.');
            final String recipeItemName = recipeItem.substring(charIndex + 1);
            final Block block = (Block) NameConverter.getField(Blocks.class, recipeItemName);
            GameRegistry.addSmelting(block, new ItemStack((Item) item), Float.parseFloat(recipe[1]));
        } else {

        }
    }

    private void addGenericRecipe() {
        final Object[] params = new Object[recipe.length];
        params[0] = recipe[0];
        params[1] = recipe[1];
        params[2] = recipe[2];
        for (int i = 3; i < recipe.length; ++i) {
            final String inputStr = recipe[i];
            if (inputStr.length() > 1) {
                if (Main.items.containsKey(inputStr)) {
                    params[i] = new ItemStack(Main.items.get(inputStr).getItem());
                } else if (Main.blocks.containsKey(inputStr)) {
                    params[i] = new ItemStack(Main.blocks.get(inputStr).getBlock());
                } else if (inputStr.startsWith(Constants.ITEM)) {
                    final int charIndex = inputStr.indexOf('.');
                    final String recipeItemName = inputStr.substring(charIndex + 1);
                    params[i] = NameConverter.getField(Items.class, recipeItemName);
                } else if (inputStr.startsWith(Constants.BLOCK)) {
                    final int charIndex = inputStr.indexOf('.');
                    final String recipeItemName = inputStr.substring(charIndex + 1);
                    params[i] = NameConverter.getField(Blocks.class, recipeItemName);
                } else {
                    params[i] = inputStr;
                }
            } else {
                params[i] = inputStr.toCharArray()[0];
            }
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((Item) item), params));
    }
}
