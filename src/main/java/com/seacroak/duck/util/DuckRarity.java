package com.seacroak.duck.util;

import com.mojang.serialization.Codec;

import java.util.function.IntFunction;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.function.ValueLists.OutOfBoundsHandling;

public enum DuckRarity implements StringIdentifiable {
  DEFAULT(0, "default"),
  GREEN(1, "green"),
  BLUE(2, "blue"),
  PURPLE(3, "purple"),
  RED(4, "red"),
  GOLD(5, "gold");

  public static final Codec<DuckRarity> CODEC = StringIdentifiable.createCodec(DuckRarity::values);
  private static final IntFunction<DuckRarity> BY_ID = ValueLists.createIdToValueFunction(DuckRarity::getId, values(), OutOfBoundsHandling.WRAP);
  private final int id;
  private final String name;

  private DuckRarity(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public static DuckRarity byId(int id) {
    return (DuckRarity) BY_ID.apply(id);
  }

  public String asString() {
    return this.name;
  }
}
