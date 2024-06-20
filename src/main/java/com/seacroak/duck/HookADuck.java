package com.seacroak.duck;

import com.seacroak.duck.networking.SoundPayload;
import com.seacroak.duck.networking.SoundPayloadPlayerless;
import com.seacroak.duck.registry.ItemGroupRegistry;
import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.registry.SoundRegistry;
import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;

import static com.seacroak.duck.Constants.DUCK_ID;

public class HookADuck implements ModInitializer {
  public static final ItemGroup DUCK_ITEMGROUP = ItemGroupRegistry.createItemGroup();

  @Override
  public void onInitialize() {
    Constants.DUCK_LOGGER.info("Hooking a duck...");

    Registry.register(Registries.ITEM_GROUP, GenericUtils.ID(DUCK_ID), DUCK_ITEMGROUP);

    MainRegistry.init();
    SoundRegistry.init();

    /* Populate biomes with DUCKS */
    BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_RIVER).or(BiomeSelectors.tag(BiomeTags.IS_OCEAN)), MainRegistry.DUCK_ENTITY.getSpawnGroup(), MainRegistry.DUCK_ENTITY, 10, 1, 5);


    PayloadTypeRegistry.playS2C().register(SoundPayload.ID, SoundPayload.CODEC);
    PayloadTypeRegistry.playS2C().register(SoundPayloadPlayerless.ID, SoundPayloadPlayerless.CODEC);
//    DuckNetworking.registerGlobalSoundPacketReceiverWithPlayer();
//    DuckNetworking.registerGlobalSoundPacketReceiverWithoutPlayer();


    Constants.DUCK_LOGGER.info("The ducks are ready!");
  }
}