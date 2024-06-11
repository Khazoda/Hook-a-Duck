package com.seacroak.duck.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DuckNetworking {

  /* Call this method clientside after sending packet when wanting to play sound */
  public static void playSoundOnClient(SoundEvent sound, World world, BlockPos pos, float volume, float pitch) {
    try {
      Vec3d vec = pos.toCenterPos();
      world.playSoundAtBlockCenter(BlockPos.ofFloored(vec), sound, SoundCategory.BLOCKS, volume, pitch, true);
    } catch (Exception e) {
      System.out.println("Caught sound exception");
    }
  }

  /* Receiver WITH Player Data*/
  public static void registerGlobalSoundPacketReceiverWithPlayer() {
    /* Registers global packet receiver in MainRegistry.class */
    ServerPlayNetworking.registerGlobalReceiver(SoundPayload.ID, (payload, context) -> {
      if (payload.playerUUID() == context.player().getUuid())
        return;
      context.player().getServer().execute(() -> {
        SoundPayload.sendPlayerPacketToClients(context.player().getServerWorld(), new SoundPayload(payload.playerUUID(), payload.pos(), payload.soundIdentifier(), payload.pitch()));
      });
    });
  }

}
