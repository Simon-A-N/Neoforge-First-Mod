package net.supersimon.avatarmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.supersimon.avatarmod.AvatarMod;

public record StatsMenuPacket() implements CustomPacketPayload {
    // Uses the new ResourceLocation factory method
    public static final Type<StatsMenuPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(AvatarMod.MODID, "open_stats"));

    // This replaces the old write() method! It tells the game how to encode/decode the packet.
    // StreamCodec.unit() is used because this packet carries no extra variables.
    public static final StreamCodec<FriendlyByteBuf, StatsMenuPacket> STREAM_CODEC =
            StreamCodec.unit(new StatsMenuPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}