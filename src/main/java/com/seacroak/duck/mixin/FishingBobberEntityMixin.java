package com.seacroak.duck.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.registry.MainRegistry;
import gay.lemmaeof.terrifictickets.TerrificTickets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
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
          DuckEntity hookedDuckEntity = (DuckEntity) hookedEntity;
          if (hookedDuckEntity != null) {
            this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 0.55F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);

            int ticketPayout = 0;
            switch (hookedDuckEntity.getVariant()) {
              case DEFAULT -> ticketPayout = 5;
              case GREEN -> ticketPayout = 10;
              case BLUE -> ticketPayout = 15;
              case PURPLE -> ticketPayout = 25;
              case RED -> ticketPayout = 45;
              case GOLD -> ticketPayout = 100;
            }

            double d = entity2.getX() - this.getX();
            double e = entity2.getY() - this.getY();
            double f = entity2.getZ() - this.getZ();

            hookedDuckEntity.setSpewParams(ticketPayout, (PlayerEntity) entity2, d,e,f);
            hookedDuckEntity.shouldSpew = true;

//            hookedDuckEntity.remove(RemovalReason.KILLED);
          }
        }
      }
    }
  }
}