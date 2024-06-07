package com.seacroak.hook_a_duck;

import com.seacroak.hook_a_duck.util.RegistryHelper;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.seacroak.hook_a_duck.Constants.DUCK_ID;

public class HookADuck implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.


    @Override
    public void onInitialize() {


        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.


        Constants.DUCK_LOGGER.info("[Hooking a Duck] The ducks are ready!");
    }
}