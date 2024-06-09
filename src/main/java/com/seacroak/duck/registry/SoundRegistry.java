package com.seacroak.duck.registry;

import com.seacroak.duck.util.GenericUtils;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public final class SoundRegistry {
  public static final SoundEvent SQUEAK = register("squeak");


  public static void init() {
  }

  private static SoundEvent register(String name) {
    return Registry.register(Registries.SOUND_EVENT, name, SoundEvent.of(GenericUtils.ID(name)));
  }

}