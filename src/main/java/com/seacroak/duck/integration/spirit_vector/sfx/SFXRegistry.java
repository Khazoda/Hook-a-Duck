package com.seacroak.duck.integration.spirit_vector.sfx;

import com.seacroak.duck.Constants;
import com.seacroak.duck.util.RegistryHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SFXRegistry {
  public static final symbolics.division.spirit.vector.sfx.SimpleSFX DuckSFX = symbolics.division.spirit.vector.api.SpiritVectorSFXApi.registerSimple(Identifier.of(Constants.DUCK_ID, "duck"), 0xffff00);
  public static final Item DUCK_ANIMA_CORE = RegistryHelper.registerItem("anima_core_duck", DuckSFX.asItem());

  public static void init() {
    Constants.DUCK_LOGGER.info("Loaded spirit vector integration");
  }
}
