package com.seacroak.duck.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;


public class ItemGroupRegistry {
  public static ItemGroup createItemGroup() {
    return FabricItemGroup.builder()
        .icon(() -> new ItemStack(MainRegistry.POPCORN))
        .displayName(Text.translatable("duck.itemGroup"))
        .entries((displayContext, entries) -> {
          entries.add(new ItemStack(MainRegistry.DUCK_ROD));
          entries.add(new ItemStack(MainRegistry.DUCK_DISPENSER));
          entries.add(new ItemStack(MainRegistry.POPCORN));
          entries.add(new ItemStack(MainRegistry.LOLLY));
          entries.add(new ItemStack(MainRegistry.CORNDUCK));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_RED));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_GREEN));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_BLUE));
          entries.add(new ItemStack(MainRegistry.DUCK_SWORD_PURPLE));
          entries.add(new ItemStack(MainRegistry.DUCK_SPAWN_EGG));
          entries.add(new ItemStack(MainRegistry.DUCK_MOUNT_SPAWN_EGG));



        }).build();
  }
}
