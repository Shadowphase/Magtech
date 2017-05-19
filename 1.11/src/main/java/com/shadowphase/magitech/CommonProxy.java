package com.shadowphase.magitech;

import com.shadowphase.magitech.init.ModBlocks;
import com.shadowphase.magitech.init.ModItems;
import com.shadowphase.magitech.world.OreGenerator;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ModItems.init();
        ModBlocks.init();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        ModItems.initRecipes();
        ModBlocks.initRecipes();
    }
}
