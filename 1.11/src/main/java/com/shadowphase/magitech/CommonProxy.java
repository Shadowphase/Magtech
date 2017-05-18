package com.shadowphase.magitech;

import com.shadowphase.magitech.init.ModBlocks;
import com.shadowphase.magitech.init.ModItems;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ModItems.init();
        ModBlocks.init();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        ModItems.initRecipes();
        ModBlocks.initRecipes();
    }
}
