package net.supersimon.avatarmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.supersimon.avatarmod.AvatarMod;

public record UpgradeStatPacket() implements CustomPacketPayload {
    public static final Type<UpgradeStatPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(AvatarMod.MODID, "upgrade_stat"));

    public static final StreamCodec<FriendlyByteBuf, UpgradeStatPacket> STREAM_CODEC =
            StreamCodec.unit(new UpgradeStatPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}