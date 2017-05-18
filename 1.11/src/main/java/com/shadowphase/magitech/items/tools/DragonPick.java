package com.shadowphase.magitech.items.tools;

import com.shadowphase.magitech.lib.Constants;

import net.minecraft.item.ItemPickaxe;

public class DragonPick extends ItemPickaxe {

    public DragonPick() {
        super(Constants.TOOL_MAT);
    }

    public DragonPick(final ToolMaterial material) {
        super(material);
    }
}
