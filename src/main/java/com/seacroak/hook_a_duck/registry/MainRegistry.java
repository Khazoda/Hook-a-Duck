package com.seacroak.hook_a_duck.registry;

import com.seacroak.hook_a_duck.util.RegistryHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class MainRegistry {
  static final Item.Settings defaultItemSettings = new Item.Settings().maxCount(64);


  public static final Item EXAMPLE_ITEM = Registry.register(Registries.ITEM, ("example_item"), new Item(defaultItemSettings));
  public static final Block EXAMPLE_BLOCK = registerBlock("example_block",new Block(AbstractBlock.Settings.create().strength(2.5f)),defaultItemSettings);
  public static final Item DUCK_ROD = registerItem("duck_rod");
  /* Registration Functions */
  private static <B extends Block> B registerBlock(String name, B block, Item.Settings itemSettings) {
    return RegistryHelper.registerBlock(name, block, itemSettings);
  }

  private static <B extends Block> B registerBlockOnly(String name, B block) {
    return RegistryHelper.registerBlockOnly(name, block);
  }

  private static <I extends BlockItem> BlockItem registerBlockItem(String name, I blockItem) {
    return RegistryHelper.registerBlockItem(name, blockItem);
  }

  private static Item registerItem(String name) {
    return RegistryHelper.registerItem(name, new Item(defaultItemSettings));
  }
}
