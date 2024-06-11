package com.seacroak.duck.networking;

import com.seacroak.duck.registry.SoundRegistry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PacketDecoder {
  public static SoundEvent decodeSoundEvent(String stringIdentifier) {
    SoundEvent returnValue;
    switch (stringIdentifier) {
      /* Custom Sounds */
      case "duck:squeak":
        returnValue = SoundRegistry.SQUEAK;
        break;
      case "duck:pop":
        returnValue = SoundRegistry.POP;
        break;

      /* MC Sounds */
      case "minecraft:block.moss.place":
        returnValue = SoundEvents.BLOCK_MOSS_PLACE;
        break;
      case "minecraft:block.wool.hit":
        returnValue = SoundEvents.BLOCK_WOOL_HIT;
        break;

      /* Default */
      default:
        returnValue = SoundRegistry.POP;
        break;
    }
    return returnValue;
  }

}
