package com.seacroak.duck.item;

import com.seacroak.duck.registry.SoundRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class DuckSword extends SwordItem {

  public DuckSword(ToolMaterial toolMaterial, Settings settings) {
    super(toolMaterial, settings);
  }

  @Override
  public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    if (attacker instanceof ServerPlayerEntity serverPlayerEntity) {
      ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
      serverWorld.spawnParticles(ParticleTypes.GUST_EMITTER_SMALL, target.getX(), target.getY(), target.getZ(), 1, 0, 0.1, 0, 0);
      serverWorld.playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundRegistry.SQUEAK, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);
      serverWorld.playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundEvents.ENTITY_BREEZE_WIND_BURST, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);

      if (target.isPlayer()) {
        Vec3d currentMovement = target.getVelocity();
        target.setVelocity(currentMovement.x, currentMovement.y + 1.25, currentMovement.z);
        target.velocityModified = true;
      } else {
        target.addVelocity(0, 0.65, 0);
      }
    }
    return super.postHit(stack, target, attacker);
  }
}
