package com.shadowphase.magitech.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ArcaneDust extends Item {

    public ArcaneDust() {
        setMaxStackSize(64);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.isRemote) {
            player.sendMessage(new TextComponentString("Item used."));
        }
        return super.onItemRightClick(world, player, hand);
    }
}
