package org.amateras_smp.amacarpet.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;

public class PacketHandler {
    // todo : support mc >= 12005 network compatibility
    //#if MC < 12005
    private static PacketByteBuf makePacketInternal(Identifier id, NbtCompound nbt) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(id.toString());
        buf.writeNbt(nbt);
        return buf;
    }

    private static CustomPayloadS2CPacket makeS2CPacket(Identifier id, NbtCompound nbt) {
        PacketByteBuf buf = makePacketInternal(id, nbt);
        return new CustomPayloadS2CPacket(buf);
    }

    private static CustomPayloadC2SPacket makeC2SPacket(Identifier id, NbtCompound nbt) {
        PacketByteBuf buf = makePacketInternal(id, nbt);
        return new CustomPayloadC2SPacket(buf);
    }

    public static void sendS2C(ServerPlayNetworkHandler networkHandler, Identifier id, NbtCompound nbt) {
        sendS2C(networkHandler, id, nbt, () -> {});
    }

    public static void sendS2C(ServerPlayNetworkHandler networkHandler, Identifier id, NbtCompound nbt, Runnable doneCallback) {
        CustomPayloadS2CPacket packet = makeS2CPacket(id, nbt);
        //#if MC >= 12002
        //$$ networkHandler.send(
        //#else
        networkHandler.sendPacket(
        //#endif
                packet,
                PacketCallbacks.always(doneCallback)
        );
    }

    public static void sendC2S(Identifier id, NbtCompound nbt) {
        sendC2S(id, nbt, () -> {});
    }

    public static void sendC2S(Identifier id, NbtCompound nbt, Runnable doneCallback) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            throw new RuntimeException("Trying to send AmaCarpet C2S packet on a dedicated server");
        }
        CustomPayloadC2SPacket packet = makeC2SPacket(id, nbt);
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null) {
            networkHandler.getConnection().send(
                    packet,
                    PacketCallbacks.always(doneCallback)
            );
        }
    }
    //#endif
}
