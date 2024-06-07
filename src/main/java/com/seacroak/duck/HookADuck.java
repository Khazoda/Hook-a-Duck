package com.seacroak.duck;

import com.seacroak.duck.registry.ItemGroupRegistry;
import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.seacroak.duck.Constants.DUCK_ID;

public class HookADuck implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
public static final ItemGroup DUCK_ITEMGROUP = ItemGroupRegistry.createItemGroup();

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, GenericUtils.ID(DUCK_ID), DUCK_ITEMGROUP);

        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.


        Constants.DUCK_LOGGER.info("[Hooking a Duck] The ducks are ready!");
    }
}