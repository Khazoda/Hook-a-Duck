package com.seacroak.duck.networking;

import com.seacroak.duck.util.GenericUtils;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record SoundPayloadPlayerless(Vec3d pos, String soundIdentifier,
                                     float pitch) implements CustomPayload {
  public static final Id<SoundPayloadPlayerless> ID = new Id<>(GenericUtils.ID("duck_sound_packet_without_player"));
  public static final PacketCodec<RegistryByteBuf, SoundPayloadPlayerless> CODEC = PacketCodec.of(SoundPayloadPlayerless::write, SoundPayloadPlayerless::read);

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }

  public static void sendNoPlayerPacketToClients(ServerWorld world, BlockPos pos, String soundIdentifier, float pitch) {
    BlockPos builderPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    /* Iterate through players that can see sound event emitter */
    PlayerLookup.tracking(world, builderPos).forEach(player -> {
      ServerPlayNetworking.send(player, new SoundPayloadPlayerless(pos.toCenterPos(), soundIdentifier, pitch));
    });
  }

  public void write(PacketByteBuf buf) {
    buf.writeDouble(pos.getX());
    buf.writeDouble(pos.getY());
    buf.writeDouble(pos.getZ());
    buf.writeString(soundIdentifier);
    buf.writeFloat(pitch);
  }

  public static SoundPayloadPlayerless read(PacketByteBuf buf) {
    Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    String soundIdentifier = buf.readString();
    float pitch = buf.readFloat();
    return new SoundPayloadPlayerless(pos, soundIdentifier, pitch);
  }


//    /* Packet originating from playerUUID */
//    public NoPlayerSoundPacket(UUID playerUUID, Vec3d pos, String soundIdentifier, float pitch) {
//        this.pos = pos;
//        this.soundIdentifier = soundIdentifier;
//        this.pitch = pitch;
//    }
//
//    /* Packet originating from server */
//    public NoPlayerSoundPacket(Vec3d pos, String soundIdentifier, float pitch) {
//        this.pos = pos;
//        this.soundIdentifier = soundIdentifier;
//        this.pitch = pitch;
//    }
//
//    public NoPlayerSoundPacket(BlockPos pos, SoundEvent soundEvent, float pitch) {
//        this(pos.toCenterPos(), soundEvent.getId().toString(), pitch);
//    }

}
