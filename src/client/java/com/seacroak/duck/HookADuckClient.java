package com.seacroak.duck;

import com.seacroak.duck.entity.DuckEntityModel;
import com.seacroak.duck.entity.DuckEntityRenderer;
import com.seacroak.duck.entity.DuckMountEntityModel;
import com.seacroak.duck.entity.DuckMountEntityRenderer;
import com.seacroak.duck.integration.spirit_vector.sfx.SFXRegistry;
import com.seacroak.duck.networking.DuckNetworking;
import com.seacroak.duck.networking.SoundPayload;
import com.seacroak.duck.networking.SoundPayloadPlayerless;
import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static com.seacroak.duck.HookADuck.spirit_vector_mod_loaded;
import static com.seacroak.duck.registry.MainRegistry.DUCK_ENTITY;
import static com.seacroak.duck.registry.MainRegistry.DUCK_MOUNT_ENTITY;

public class HookADuckClient implements ClientModInitializer {
  public static final EntityModelLayer MODEL_DUCK_LAYER = new EntityModelLayer(GenericUtils.ID("duck"), "main");
  public static final EntityModelLayer MODEL_DUCK_MOUNT_LAYER = new EntityModelLayer(GenericUtils.ID("duck_mount"), "main");

  @Override
  public void onInitializeClient() {
    PayloadTypeRegistry.playC2S().register(SoundPayload.ID, SoundPayload.CODEC);
    PayloadTypeRegistry.playC2S().register(SoundPayloadPlayerless.ID, SoundPayloadPlayerless.CODEC);

    EntityRendererRegistry.register(DUCK_ENTITY, DuckEntityRenderer::new);
    EntityModelLayerRegistry.registerModelLayer(MODEL_DUCK_LAYER, DuckEntityModel::getTexturedModelData);

    EntityRendererRegistry.register(DUCK_MOUNT_ENTITY, DuckMountEntityRenderer::new);
    EntityModelLayerRegistry.registerModelLayer(MODEL_DUCK_MOUNT_LAYER, DuckMountEntityModel::getTexturedModelData);

    ParticleFactoryRegistry.getInstance().register(MainRegistry.RARE_SPARKLE, FlameParticle.Factory::new);
    ParticleFactoryRegistry.getInstance().register(MainRegistry.DUCKS, FlameParticle.Factory::new);
    if (spirit_vector_mod_loaded)
      ParticleFactoryRegistry.getInstance().register(SFXRegistry.DuckSFX.particleType(), CloudParticle.CloudFactory::new);

    BlockRenderLayerMap.INSTANCE.putBlock(MainRegistry.DUCK_DISPENSER, RenderLayer.getCutout());

    /* Sound Event Networking Packet Client Receipt */
    ClientPlayNetworking.registerGlobalReceiver(SoundPayload.ID, (payload, context) -> {
      if (context.client() == null) return;
      assert context.client().player != null;
      if (payload.playerUUID() == context.client().player.getUuid())
        return;
      context.client().execute(() -> {
        if (context.client().world == null)
          return;
        DuckNetworking.playSoundOnClient(payload.soundEvent(), context.client().world, payload.pos(), 1f, payload.pitch());
      });
    });

    /* Sound Event Networking Packet Client Receipt */
    ClientPlayNetworking.registerGlobalReceiver(SoundPayloadPlayerless.ID, (payload, context) -> {
      if (context.client() == null) return;
      context.client().execute(() -> {
        if (context.client().world == null)
          return;
        DuckNetworking.playSoundOnClient(payload.soundEvent(), context.client().world, payload.pos(), 1f, payload.pitch());
      });
    });
  }
}