package com.shadowphase.magitech;

import com.shadowphase.magitech.init.ModBlocks;
import com.shadowphase.magitech.init.ModItems;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        ModBlocks.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModItems.initRecipes();
        ModBlocks.initRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
