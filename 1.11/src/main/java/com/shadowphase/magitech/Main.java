package com.shadowphase.magitech;

import java.util.HashMap;
import java.util.Map;

import com.shadowphase.magitech.lib.ModBlock;
import com.shadowphase.magitech.lib.ModItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION, dependencies = Main.DEPENDENCIES)
public class Main {
    public static final String MODID = "magitech";
    public static final String MODNAME = "Magitech";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:forge@[13.20.0.2296,);";// after:shadowslib";
    public static final String RESOURCE_PREFIX = MODID.toLowerCase() + ":";
    public static final Map<String, ModItem> items = new HashMap<String, ModItem>();
    public static final Map<String, ModBlock> blocks = new HashMap<String, ModBlock>();

    @Instance(MODID)
    public static Main instance;

    @SidedProxy(clientSide = "com.shadowphase.magitech.ClientProxy", serverSide = "com.shadowphase.magitech.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {

        proxy.preInit(event);
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {

        proxy.init(event);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }

    public static CreativeTabs itemTab = new CreativeTabs(Main.RESOURCE_PREFIX + "tab_magitech") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(items.get("arcane_dust").getItem());
        }
    };
}
