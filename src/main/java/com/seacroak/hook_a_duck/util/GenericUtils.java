package com.seacroak.hook_a_duck.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import static com.seacroak.hook_a_duck.Constants.DUCK_ID;

public class GenericUtils {
  @NotNull
  public static Identifier ID(@NotNull String path) {
    return Identifier.of(DUCK_ID, path);
  }
}
