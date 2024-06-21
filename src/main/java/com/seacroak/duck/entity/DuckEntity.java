package com.seacroak.duck.entity;

import com.seacroak.duck.loot_table.DuckLootTable;
import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.registry.SoundRegistry;
import com.seacroak.duck.util.DuckRarity;
import gay.lemmaeof.terrifictickets.TerrificTickets;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DuckEntity extends WaterCreatureEntity implements VariantHolder<DuckRarity> {
  private static final TrackedData<Integer> RARITY = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.INTEGER);
  private int tickCounter = 0;
  public boolean shouldSpew = false;

  int ticketPayout;
  DuckRarity rarityHooked = DuckRarity.DEFAULT;
  int ticketsPaidOut = 0;
  PlayerEntity hooker;
  double d;
  double e;
  double f;

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
    return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2).add(EntityAttributes.GENERIC_ARMOR, 0.5f);
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

  public void setSpewParams(int ticketPayout, DuckRarity rarity, PlayerEntity hooker, double d, double e, double f) {
    this.ticketPayout = ticketPayout;
    this.rarityHooked = rarity;
    this.hooker = hooker;
    this.d = d;
    this.e = e;
    this.f = f;
  }

  public void spewWeightedLoot(World world) {
    Item[] lootTable = new Item[0];
    switch (this.rarityHooked) {
      case DEFAULT -> {
        lootTable = DuckLootTable.DEFAULT_LOOT_TABLE;
      }
      case GREEN -> {
        lootTable = DuckLootTable.GREEN_LOOT_TABLE;
      }
      case BLUE -> {
        lootTable = DuckLootTable.BLUE_LOOT_TABLE;
      }
      case PURPLE -> {
        lootTable = DuckLootTable.PURPLE_LOOT_TABLE;
      }
      case RED -> {
        lootTable = DuckLootTable.RED_LOOT_TABLE;
      }
      case GOLD -> {
        lootTable = DuckLootTable.GOLD_LOOT_TABLE;
      }
    }

    if (world.getRandom().nextBoolean()) {
      ItemStack loot = new ItemStack(lootTable[world.getRandom().nextBetween(0, lootTable.length - 1)]);
      ItemEntity lootEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), loot);
      lootEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.125, f * 0.1);
      world.spawnEntity(lootEntity);
      this.playSound(SoundRegistry.TRUMPET, 1F, 1.0F);

      this.d = hooker.getX() - this.getX();
      this.e = hooker.getY() - this.getY();
      this.f = hooker.getZ() - this.getZ();
    }
  }

  public void spewFiveTickets() {
    ItemStack fiveTickets = new ItemStack(TerrificTickets.TICKET);
    fiveTickets.setCount(5);
    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), fiveTickets);
    itemEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.125, f * 0.1);
    this.getWorld().spawnEntity(itemEntity);
    this.playSound(SoundRegistry.POP, 1F, 1.0F + ((float) ticketsPaidOut / 5) / 10);


    this.d = hooker.getX() - this.getX();
    this.e = hooker.getY() - this.getY();
    this.f = hooker.getZ() - this.getZ();
  }


  @Override
  public void tick() {
    super.tick();
    if (tickCounter % 2 == 0) {
      if (this.shouldSpew) {
        spewFiveTickets();
        ticketsPaidOut += 5;
        if (ticketsPaidOut >= ticketPayout) {
          spewWeightedLoot(getWorld());
          this.shouldSpew = false;
          ServerWorld serverWorld = (ServerWorld) this.getWorld();
          for (int i = 0; i < 5; i++) {
            serverWorld.spawnParticles(ParticleTypes.POOF, this.getX(), this.getY() + 0.1, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0, this.getWidth(), 0.2F);
            serverWorld.spawnParticles(MainRegistry.DUCKS, this.getX() + (this.random.nextFloat() - this.random.nextFloat()), this.getY() + 0.1, this.getZ() + (this.random.nextFloat() - this.random.nextFloat()), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth() / 5, 0.0, this.getWidth() / 5, 0.05F);
          }
          this.playSound(SoundRegistry.SQUEAK, 0.55F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
          this.remove(RemovalReason.KILLED);
        }
      }
    }
    tickCounter++;
  }

  @Override
  public DuckRarity getVariant() {
    return DuckRarity.byId(this.getDuckRarity() & 255);
  }

  @Override
  public void tickMovement() {
    super.tickMovement();
    if (this.getVariant() == DuckRarity.GOLD) {
      this.getWorld().addParticle(ParticleTypes.ENCHANT, this.getParticleX(0.6), this.getRandomBodyY(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
      this.getWorld().addParticle(MainRegistry.RARE_SPARKLE, this.getParticleX(0.6), this.getRandomBodyY(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
    }
  }

  @Override
  public void onDeath(DamageSource damageSource) {
    if (!getWorld().isClient()) {
      if (damageSource.isOf(DamageTypes.PLAYER_ATTACK)) {
        for (int i = 0; i < 2; i++) {
          dropItem(TerrificTickets.TICKET);
        }
        dropItem(Items.DEAD_BUSH);
        damageSource.getAttacker().sendMessage(Text.literal("Try hooking the duck instead.."));
      }
    }
    for (int i = 0; i < 5; i++) {
      this.getWorld().addParticle(ParticleTypes.POOF, this.getX(), this.getY() + 0.1, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0);
      this.getWorld().addParticle(MainRegistry.DUCKS, this.getX() + (this.random.nextFloat() - this.random.nextFloat()), this.getY() + 0.1, this.getZ() + (this.random.nextFloat() - this.random.nextFloat()), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth() / 5, 0.0);
    }
    super.onDeath(damageSource);
  }


  @Nullable
  @Override
  public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
    Random random = world.getRandom();
    DuckRarity duckRarity = DuckRarity.DEFAULT;

    int randomRarityInt = random.nextInt(100);

    if (randomRarityInt <= 70) {
      duckRarity = DuckRarity.DEFAULT;
    } else if (randomRarityInt <= 85) {
      if (random.nextBetween(0, 1) == 0) {
        duckRarity = DuckRarity.GREEN;
      } else {
        duckRarity = DuckRarity.BLUE;
      }
    } else if (randomRarityInt <= 95) {
      if (random.nextBetween(0, 1) == 0) {
        duckRarity = DuckRarity.PURPLE;
      } else {
        duckRarity = DuckRarity.RED;
      }
    } else if (randomRarityInt <= 100) {
      duckRarity = DuckRarity.GOLD;
    }
    entityData = new DuckData(duckRarity);

    this.setDuckRarity(duckRarity);

    float duckSize = random.nextFloat() * 2f;
    float duckSizeMultiplier = 1;
    switch (duckRarity) {
      case DEFAULT -> duckSizeMultiplier = 0.8f;
      case GREEN, BLUE -> duckSizeMultiplier = 0.9f;
      case PURPLE, RED -> duckSizeMultiplier = 1.2f;
      case GOLD -> duckSizeMultiplier = 2f;
    }
    Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_SCALE)).setBaseValue(Math.clamp(duckSizeMultiplier * duckSize, 0.75, 2));

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
