package com.seacroak.duck.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;


public class ItemGroupRegistry {
  public static ItemGroup createItemGroup() {
    return FabricItemGroup.builder()
        .icon(() -> new ItemStack(MainRegistry.EXAMPLE_ITEM))
        .displayName(Text.translatable("duck.itemGroup"))
        .entries((displayContext, entries) -> {
          entries.add(new ItemStack(MainRegistry.EXAMPLE_ITEM));
          entries.add(new ItemStack(MainRegistry.DUCK_ROD));

        }).build();
  }
}
