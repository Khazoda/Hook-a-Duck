package com.seacroak.duck.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.text.Text;


public class ItemGroupRegistry {
  public static ItemGroup createItemGroup() {
    return FabricItemGroup.builder()
        .icon(() -> new ItemStack(MainRegistry.POPCORN))
        .displayName(Text.translatable("duck.itemGroup"))
        .entries((displayContext, entries) -> {
          entries.add(new ItemStack(MainRegistry.EXAMPLE_BLOCK));
          entries.add(new ItemStack(MainRegistry.EXAMPLE_ITEM));
          entries.add(new ItemStack(MainRegistry.DUCK_ROD));
          entries.add(new ItemStack(MainRegistry.POPCORN));
          entries.add(new ItemStack(MainRegistry.LOLLY));
          entries.add(new ItemStack(MainRegistry.CORNDUCK));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_RED));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_GREEN));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_BLUE));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_PURPLE));

        }).build();
  }
}
