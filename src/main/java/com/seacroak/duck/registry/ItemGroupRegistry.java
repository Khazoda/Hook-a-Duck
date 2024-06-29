package com.seacroak.duck.registry;

import com.seacroak.duck.integration.spirit_vector.sfx.SFXRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static com.seacroak.duck.HookADuck.spirit_vector_mod_loaded;


public class ItemGroupRegistry {
  public static ItemGroup createItemGroup() {
    return FabricItemGroup.builder()
        .icon(() -> new ItemStack(MainRegistry.DUCK_MOUNT_SPAWN_EGG))
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
          if (spirit_vector_mod_loaded) entries.add(new ItemStack(SFXRegistry.DUCK_ANIMA_CORE));
        }).build();
  }
}
