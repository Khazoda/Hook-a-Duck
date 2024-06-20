package com.seacroak.duck.networking;

import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public record SoundPayload(UUID playerUUID, BlockPos pos, SoundEvent soundEvent, float pitch) implements CustomPayload {
  public static final CustomPayload.Id<SoundPayload> ID = new Id<>(GenericUtils.ID("duck_sound_packet_with_player"));

  public static final PacketCodec<RegistryByteBuf, SoundPayload> CODEC = PacketCodec.tuple(
      Uuids.PACKET_CODEC, SoundPayload::playerUUID,
      BlockPos.PACKET_CODEC, SoundPayload::pos,
      SoundEvent.PACKET_CODEC, SoundPayload::soundEvent,
      PacketCodecs.FLOAT, SoundPayload::pitch,
      SoundPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }

  public static void sendPlayerPacketToClients(ServerWorld world, SoundPayload payload) {
    BlockPos builderPos = new BlockPos(payload.pos.getX(), payload.pos.getY(), payload.pos.getZ());
    /* Iterate through players that can see sound event emitter */
    PlayerLookup.tracking(world, builderPos).forEach(player -> {
      if (player.getUuid() == payload.playerUUID)
        return;
      ServerPlayNetworking.send(player, new SoundPayload(payload.playerUUID, payload.pos, payload.soundEvent, payload.pitch));
    });
  }
}
