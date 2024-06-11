package com.seacroak.duck.networking;

import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public record SoundPayload(UUID playerUUID, Vec3d pos, String soundIdentifier, float pitch) implements CustomPayload {
  public static final Id<SoundPayload> ID = new Id<>(GenericUtils.ID("duck_sound_packet_with_player"));
  public static final PacketCodec<RegistryByteBuf, SoundPayload> CODEC = PacketCodec.of(SoundPayload::write, SoundPayload::read);


  public SoundPayload(PlayerEntity player, BlockPos pos, SoundEvent soundEvent, float pitch) {
    this(player.getUuid(), pos.toCenterPos(), soundEvent.getId().toString(), pitch);
  }

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }

  public static void sendPlayerPacketToClients(ServerWorld world, SoundPayload payload) {
    BlockPos builderPos = new BlockPos((int) payload.pos.x, (int) payload.pos.y, (int) payload.pos.z);
    /* Iterate through players that can see sound event emitter */
    PlayerLookup.tracking(world, builderPos).forEach(player -> {
      if (player.getUuid() == payload.playerUUID)
        return;
      ServerPlayNetworking.send(player, new SoundPayload(payload.playerUUID, payload.pos, payload.soundIdentifier, payload.pitch));
    });
  }

  public void write(PacketByteBuf buf) {
    buf.writeUuid(playerUUID);
    buf.writeDouble(pos.x);
    buf.writeDouble(pos.y);
    buf.writeDouble(pos.z);
    buf.writeString(soundIdentifier);
    buf.writeFloat(pitch);
  }

  public static SoundPayload read(PacketByteBuf buf) {
    UUID player = buf.readUuid();
    Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    String soundIdentifier = buf.readString();
    float pitch = buf.readFloat();
    return new SoundPayload(player, pos, soundIdentifier, pitch);
  }
}
