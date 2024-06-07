package com.seacroak.duck.registry;

import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.util.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.seacroak.duck.util.RegistryHelper.newID;

public class MainRegistry {
  static final Item.Settings defaultItemSettings = new Item.Settings().maxCount(64);

  public static final Item EXAMPLE_ITEM = Registry.register(Registries.ITEM, Identifier.of("example_item"), new Item(defaultItemSettings));
  //  public static final Block EXAMPLE_BLOCK = registerBlock("example_block",new Block(AbstractBlock.Settings.create().strength(2.5f)),defaultItemSettings);
  public static final Item DUCK_ROD = registerItem("duck_rod");

  public static final EntityType<DuckEntity> DUCK_ENTITY = Registry.register(
      Registries.ENTITY_TYPE,
      newID("duck"),
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuckEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

  public static void init() {
    FabricDefaultAttributeRegistry.register(DUCK_ENTITY, DuckEntity.createMobAttributes());

  }


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
