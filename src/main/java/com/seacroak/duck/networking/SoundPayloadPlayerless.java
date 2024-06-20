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
import net.minecraft.util.math.BlockPos;

public record SoundPayloadPlayerless(BlockPos pos, SoundEvent soundEvent,
                                     float pitch) implements CustomPayload {
  public static final Id<SoundPayloadPlayerless> ID = new Id<>(GenericUtils.ID("duck_sound_packet_without_player"));
  public static final PacketCodec<RegistryByteBuf, SoundPayloadPlayerless> CODEC = PacketCodec.tuple(
      BlockPos.PACKET_CODEC, SoundPayloadPlayerless::pos,
      SoundEvent.PACKET_CODEC, SoundPayloadPlayerless::soundEvent,
      PacketCodecs.FLOAT, SoundPayloadPlayerless::pitch,
      SoundPayloadPlayerless::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }

  public static void sendNoPlayerPacketToClients(ServerWorld world, SoundPayloadPlayerless payload) {
    BlockPos builderPos = new BlockPos(payload.pos.getX(), payload.pos.getY(), payload.pos.getZ());
    /* Iterate through players that can see sound event emitter */
    PlayerLookup.tracking(world, builderPos).forEach(player -> {
      ServerPlayNetworking.send(player, new SoundPayloadPlayerless(payload.pos, payload.soundEvent, payload.pitch));
    });
  }

}
