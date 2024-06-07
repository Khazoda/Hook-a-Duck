package com.seacroak.duck.entity;

import com.seacroak.duck.util.DuckRarity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DuckEntity extends WaterCreatureEntity implements VariantHolder<DuckRarity> {
  private static final TrackedData<Integer> RARITY = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.INTEGER);

  public DuckEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
    super(entityType, world);
  }

  protected void initDataTracker(DataTracker.Builder builder) {
    super.initDataTracker(builder);
    builder.add(RARITY, 0);
  }

  @Override
  protected void initGoals() {
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new SwimAroundGoal(this, 5, 2));
    this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
    this.goalSelector.add(3, new LookAroundGoal(this));
  }

  public static DefaultAttributeContainer.Builder createDuckAttributes() {
    return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
        .add(EntityAttributes.GENERIC_ARMOR, 0.5f);
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putInt("Variant", this.getDuckRarity());
  }

  @Override
  public void readCustomDataFromNbt(NbtCompound nbt) {
    super.readCustomDataFromNbt(nbt);
    this.setDuckRarity(nbt.getInt("Variant"));
  }

  private int getDuckRarity() {
    return this.dataTracker.get(RARITY);
  }

  private void setDuckRarity(DuckRarity rarity) {
    this.setDuckRarity(rarity.getId() & 0xFF);
  }

  private void setDuckRarity(int variant) {
    this.dataTracker.set(RARITY, variant);
  }

  @Override
  public void setVariant(DuckRarity variant) {
  }

  @Override
  public DuckRarity getVariant() {
    return DuckRarity.byId(this.getDuckRarity() & 255);
  }

  @Nullable
  @Override
  public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
    Random random = world.getRandom();
    DuckRarity duckRarity;
    if (entityData instanceof DuckEntity.DuckData) {
      duckRarity = ((DuckData) entityData).rarity;
    } else {
      duckRarity = Util.getRandom(DuckRarity.values(), random);
      entityData = new DuckEntity.DuckData(duckRarity);
    }
    this.setDuckRarity(duckRarity);
    return super.initialize(world, difficulty, spawnReason, entityData);
  }

  public static class DuckData extends PassiveEntity.PassiveData {
    public final DuckRarity rarity;

    public DuckData(DuckRarity rarity) {
      super(true);
      this.rarity = rarity;
    }
  }
}
