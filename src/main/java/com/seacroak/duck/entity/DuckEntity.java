package com.seacroak.duck.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DuckEntity extends PathAwareEntity {
  public DuckEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  protected void initGoals() {
    this.goalSelector.add(0, new SwimGoal(this));

    this.goalSelector.add(4, new WanderAroundFarGoal(this, 1D));
    this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
    this.goalSelector.add(6, new LookAroundGoal(this));
  }

  public static DefaultAttributeContainer.Builder createDuckAttributes() {
    return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.2f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,2)
        .add(EntityAttributes.GENERIC_ARMOR,0.5f);
  }
}
