package com.seacroak.duck.entity;

import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.util.DuckRarity;
import gay.lemmaeof.terrifictickets.TerrificTickets;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class DuckEntity extends WaterCreatureEntity implements VariantHolder<DuckRarity> {
  private static final TrackedData<Integer> RARITY = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.INTEGER);
  private int tickCounter = 0;
  public boolean shouldSpew = false;

  int ticketPayout;
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
    return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 4)
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

  public void setSpewParams(int ticketPayout,PlayerEntity hooker, double d, double e, double f) {
    this.ticketPayout = ticketPayout;
    this.hooker = hooker;
    this.d = d;
    this.e = e;
    this.f = f;
  }

  public void spew() {
    ItemStack fiveTickets = new ItemStack(TerrificTickets.TICKET);
    fiveTickets.setCount(5);
    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), fiveTickets);
    itemEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08, f * 0.1);
    this.getWorld().spawnEntity(itemEntity);

    this.d = hooker.getX() - this.getX();
    this.e = hooker.getY() - this.getY();
    this.f = hooker.getZ() - this.getZ();
  }

  @Override
  public void tick() {
    super.tick();
    if (tickCounter % 2 == 0) {
      if (this.shouldSpew) {
        spew();
        ticketsPaidOut += 5;
        if (ticketsPaidOut >= ticketPayout){
          this.shouldSpew = false;
          ServerWorld serverWorld = (ServerWorld) this.getWorld();
          for (int i = 0; i < 10; i++) {
            serverWorld.spawnParticles(ParticleTypes.GLOW, this.getX(), this.getY() + 0.1, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), (double) this.getWidth(), 0.0, (double) this.getWidth(), 0.2F);
          }
          this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 0.55F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
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
    for (int i = 0; i < 5; i++) {
      dropItem(TerrificTickets.TICKET);
    }
    for (int i = 0; i < 10; i++) {
      this.getWorld().addParticle(ParticleTypes.ENCHANT, this.getX(), this.getY(), this.getZ(), 0, 0.1f, 0);
      this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(0.6), this.getRandomBodyY(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
      this.getWorld().addParticle(
          ParticleTypes.BUBBLE_POP,
          (double) this.getPos().getX() + 0.13125F + 0.7375F * (double) random.nextFloat(),
          (double) this.getPos().getY() + 0.13125F + (double) random.nextFloat() * (1.0 - 0.13125F),
          (double) this.getPos().getZ() + 0.13125F + 0.7375F * (double) random.nextFloat(),
          0,
          0, 0
      );
    }
    super.onDeath(damageSource);
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
