package com.seacroak.duck;

import com.seacroak.duck.registry.ItemGroupRegistry;
import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.seacroak.duck.Constants.DUCK_ID;

public class HookADuck implements ModInitializer {
  public static final ItemGroup DUCK_ITEMGROUP = ItemGroupRegistry.createItemGroup();

  @Override
  public void onInitialize() {
    Registry.register(Registries.ITEM_GROUP, GenericUtils.ID(DUCK_ID), DUCK_ITEMGROUP);

    Constants.DUCK_LOGGER.info("[Hooking a Duck] The ducks are ready!");
  }
}