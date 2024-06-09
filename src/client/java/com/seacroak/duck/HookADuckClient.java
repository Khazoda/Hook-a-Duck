package com.seacroak.duck;

import com.seacroak.duck.entity.DuckEntityModel;
import com.seacroak.duck.entity.DuckEntityRenderer;
import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static com.seacroak.duck.registry.MainRegistry.DUCK_ENTITY;

public class HookADuckClient implements ClientModInitializer {
  public static final EntityModelLayer MODEL_DUCK_LAYER = new EntityModelLayer(GenericUtils.ID("duck"), "main");

  @Override
  public void onInitializeClient() {

    EntityRendererRegistry.register(DUCK_ENTITY, DuckEntityRenderer::new);
    EntityModelLayerRegistry.registerModelLayer(MODEL_DUCK_LAYER, DuckEntityModel::getTexturedModelData);

    ParticleFactoryRegistry.getInstance().register(MainRegistry.RARE_SPARKLE, FlameParticle.Factory::new);
    ParticleFactoryRegistry.getInstance().register(MainRegistry.DUCKS, FlameParticle.Factory::new);

  }
}