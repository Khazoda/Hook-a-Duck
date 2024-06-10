package com.seacroak.duck.registry;

import com.seacroak.duck.Constants;
import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.entity.DuckMountEntity;
import com.seacroak.duck.item.DuckSword;
import com.seacroak.duck.item.FoodItem;
import com.seacroak.duck.item.MountSpawnerItem;
import com.seacroak.duck.util.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.seacroak.duck.util.GenericUtils.ID;
import static com.seacroak.duck.util.RegistryHelper.newID;

public class MainRegistry {
  static final Item.Settings defaultItemSettings = new Item.Settings().maxCount(64);
  static final Item.Settings singletonItemSettings = new Item.Settings().maxCount(1);

  /* Blocks */
  public static final Block EXAMPLE_BLOCK = registerBlock("example_block", new Block(AbstractBlock.Settings.create().strength(2.5f)), defaultItemSettings);

  /* Generic Items  */
  public static final Item EXAMPLE_ITEM = Registry.register(Registries.ITEM, Identifier.of("example_item"), new Item(defaultItemSettings));
  //public static final Item DUCK_ROD = registerItem("duck_rod");
  //public static final Item DUCK_MOUNT_ENTITY_SPAWN_EGG = registerItem("duck_mount_entity_spawn_egg", new SpawnEggItem(MainRegistry.DUCK_MOUNT_ENTITY, 0xE53935, 0xFEFEFE, new Item.Settings()));


  /* Consumables */
  public static final FoodComponent BIG_FOOD = (new FoodComponent.Builder()).nutrition(8).saturationModifier(0.3F).build();
  public static final FoodComponent SMALL_FOOD = (new FoodComponent.Builder()).nutrition(4).saturationModifier(0.3F).build();
  public static final Item POPCORN = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "popcorn"),
      new Item(new Item.Settings().food(MainRegistry.BIG_FOOD)));
  public static final Item LOLLY = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_pop"),
      new Item(new Item.Settings().food(MainRegistry.SMALL_FOOD)));
  public static final Item CORNDUCK = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_cornduck"),
      new FoodItem(new Item.Settings().food(MainRegistry.BIG_FOOD), Items.STICK));


  /*Tools*/
  public static final Item DUCK_SWORD = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_sword"), new DuckSword(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(DuckSword.createAttributeModifiers(ToolMaterials.WOOD, 0, -1f))));
  public static final Item DUCK_SWORD_RED = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_sword_red"), new DuckSword(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(DuckSword.createAttributeModifiers(ToolMaterials.WOOD, 0, -1f))));
  public static final Item DUCK_SWORD_GREEN = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_sword_green"), new DuckSword(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(DuckSword.createAttributeModifiers(ToolMaterials.WOOD, 0, -1f))));
  public static final Item DUCK_SWORD_BLUE = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_sword_blue"), new DuckSword(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(DuckSword.createAttributeModifiers(ToolMaterials.WOOD, 0, -1f))));
  public static final Item DUCK_SWORD_PURPLE = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_sword_purple"), new DuckSword(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(DuckSword.createAttributeModifiers(ToolMaterials.WOOD, 0, -1f))));

  /* Entities */
  public static final EntityType<DuckEntity> DUCK_ENTITY = Registry.register(
      Registries.ENTITY_TYPE,
      newID("duck"),
      FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT, DuckEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

  public static final Item DUCK_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_spawn_egg"), new SpawnEggItem(
      MainRegistry.DUCK_ENTITY,
      5526528,
      16776960,
      new Item.Settings().fireproof().maxCount(64)));

  public static final EntityType<DuckMountEntity> DUCK_MOUNT_ENTITY = Registry.register(
      Registries.ENTITY_TYPE,
      newID("duck_mount"),
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuckMountEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

  public static final Item DUCK_MOUNT_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(Constants.DUCK_ID, "duck_mount_spawn_egg"), new MountSpawnerItem<>(
      MainRegistry.DUCK_MOUNT_ENTITY,
      new Item.Settings().fireproof().maxCount(1)));

  /* Particles */
  public static final SimpleParticleType RARE_SPARKLE = Registry.register(Registries.PARTICLE_TYPE, ID("rare_sparkle"), FabricParticleTypes.simple());
  public static final SimpleParticleType DUCKS = Registry.register(Registries.PARTICLE_TYPE, ID("ducks"), FabricParticleTypes.simple());

  public static void init() {
    FabricDefaultAttributeRegistry.register(DUCK_ENTITY, DuckEntity.createDuckAttributes());
    FabricDefaultAttributeRegistry.register(DUCK_MOUNT_ENTITY, DuckMountEntity.createDuckMountAttributes());

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

  private static Item registerItem(String name, SpawnEggItem spawnEggItem) {
    return RegistryHelper.registerItem(name, new Item(defaultItemSettings));
  }
}
