package com.seacroak.duck.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.registry.MainRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends Entity {
  @Shadow
  private @Nullable Entity hookedEntity;

  public FishingBobberEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Inject(at = @At("TAIL"), method = "pullHookedEntity")
  private void init(CallbackInfo info, @Local(ordinal = 1) Entity entity2) {

    if (!this.getWorld().isClient()) {
      if (hookedEntity != null) {
        if (hookedEntity.getType() == MainRegistry.DUCK_ENTITY) {
          Vec3d vec3d = new Vec3d(entity2.getX() - this.getX(), entity2.getY() - (this.getY() - 2), entity2.getZ() - this.getZ()).multiply(0.5);
          if (hookedEntity != null) {
            hookedEntity.setVelocity(hookedEntity.getVelocity().add(vec3d));

//            hookedEntity.remove(RemovalReason.DISCARDED);
          }
        }
      }
    }
    this.playSound(SoundEvents.BLOCK_BELL_USE, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
    for (int i = 0; i < 10; i++) {
      this.getWorld().addParticle(ParticleTypes.ENCHANT, this.getX(), this.getY(), this.getZ(), 0, 0.1f, 0);
      this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(0.6), this.getRandomBodyY(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
    }

  }
}