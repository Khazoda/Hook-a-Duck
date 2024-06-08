package com.seacroak.duck.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

import java.util.Objects;

public class RareSparkleParticle extends SpriteBillboardParticle {

  public RareSparkleParticle(ParticleEffect effect, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
    super(world, x, y, z, velX, velY, velZ);

    setSprite(Objects.requireNonNull(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(Items.BARRIER)).getParticleSprite());
  }

  @Override
  public ParticleTextureSheet getType() {
    return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
  }
}
