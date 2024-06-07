package com.seacroak.duck.mixin;

import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.registry.MainRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
  @Shadow
  private @Nullable Entity hookedEntity;

  @Inject(at = @At("TAIL"), method = "pullHookedEntity")
  private void init(CallbackInfo info) {

    if (hookedEntity != null) {
      if (hookedEntity.getType() == MainRegistry.DUCK_ENTITY) {
        hookedEntity.addVelocity(0, 1, 0);
      }
    }
  }
}