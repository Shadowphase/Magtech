package com.shadowphase.magitech.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemItem1 extends Item {

    public ItemItem1() {
        setMaxStackSize(64);
    }

    public void addRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(this, 4), Items.BONE, Items.BONE, Items.EGG,
                new ItemStack(Blocks.LOG, 1, 2));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            player.sendMessage(new TextComponentString("Item used."));
        }
        return super.onItemRightClick(world, player, hand);
    }
}
