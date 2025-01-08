package org.amateras_smp.amacarpet.network.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;
import org.amateras_smp.amacarpet.network.IPacket;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.utils.PlayerUtil;

import java.nio.charset.StandardCharsets;

public class HandshakePacket extends IPacket {

    private final String version;

    public HandshakePacket(String version) {
        this.version = version;
    }

    public HandshakePacket(byte[] bytes) {
        this.version = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] encode() {
        return version.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void onServer(ServerPlayerEntity player) {
        AmaCarpetServer.minecraft_server.execute(() -> {
            AmaCarpetServer.LOGGER.info("[AmaCarpet] Player {} logged in with AmaCarpet-Client version {}.", player.getName().getString(), version);
            PlayerUtil.markAsVerified(player.getName().getString());
            HandshakePacket handshakePacket = new HandshakePacket(AmaCarpet.getVersion());
            PacketHandler.sendS2C(handshakePacket, player);
        });
    }

    @Override
    public void onClient() {
        MinecraftClient.getInstance().execute(() -> {
            AmaCarpetClient.LOGGER.info("[AmaCarpet] Logging into AmaCarpet-Server version {}.", version);
        });
    }
}
