package com.seacroak.duck.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class DuckEntity extends PathAwareEntity {
  public DuckEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
    super(entityType, world);
  }
}
