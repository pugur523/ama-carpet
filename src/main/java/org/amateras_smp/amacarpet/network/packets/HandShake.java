package org.amateras_smp.amacarpet.network.packets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;
import org.amateras_smp.amacarpet.network.PacketHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.amateras_smp.amacarpet.AmaCarpet.MOD_VERSION;
import static org.amateras_smp.amacarpet.network.RegisterPackets.HANDSHAKE_C2S;

public class HandShake {
    public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static final ConcurrentHashMap<ServerPlayerEntity, Boolean> playerModStatus = new ConcurrentHashMap<>();

    public static void serverHandler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender sender) {
        playerModStatus.put(player, true);
        AmaCarpetServer.LOGGER.info("[AmaCarpetServer] {} logged in with amacarpet-client({})!", player.getName().getString(), Objects.requireNonNull(packetByteBuf.readNbt()).getString("mod_verseion"));
    }

    public static void clientHandler(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender sender) {
        AmaCarpetClient.LOGGER.info("[AmaCarpetClient] received amacarpet-server({}) handshake packet!", Objects.requireNonNull(packetByteBuf.readNbt()).getString("mod_version"));
        PacketByteBuf packetByteBuf1 = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf1.writeString(MOD_VERSION);
        // sender.sendPacket(HANDSHAKE_C2S, packetByteBuf1);
        NbtCompound nbt = new NbtCompound();
        nbt.put("mod_version", NbtString.of(MOD_VERSION));
        PacketHandler.sendC2S(HANDSHAKE_C2S, nbt);
    }
}
