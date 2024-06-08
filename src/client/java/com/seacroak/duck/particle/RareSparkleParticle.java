package com.seacroak.duck.particle;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;

public class RareSparkleParticle extends SpriteBillboardParticle {

  protected RareSparkleParticle(ClientWorld clientWorld, double d, double e, double f) {
    super(clientWorld, d, e, f);
  }

  @Override
  public ParticleTextureSheet getType() {
    return null;
  }
}
