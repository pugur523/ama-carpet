package org.amateras_smp.amacarpet.network;

import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketHandler {
    private static final List<Packet> packetRegistry = new ArrayList<>();

    private record Packet(String key, Class<? extends IPacket> clazz) {
    }

    static {
        packetRegistry.add(new Packet("handshake", HandshakePacket.class));
    }

    private static IPacket decode(byte[] raw) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(raw); DataInputStream dis = new DataInputStream(bais)) {
            String key = dis.readUTF();
            int len = dis.readInt();
            byte[] data = new byte[len]; dis.readFully(data);

            for (Packet packet : packetRegistry) if (key.equals(packet.key)) {
                try {
                    return packet.clazz.getConstructor(byte[].class).newInstance((Object) data);
                } catch (Exception e) {
                    AmaCarpet.LOGGER.error("Failed to decode packet {}", key);
                    AmaCarpet.LOGGER.error(e);
                    return null;
                }
            }
            AmaCarpet.LOGGER.error("Unknown Packet {}", key);
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Unknown Error: \n" + e);
        }

        return null;
    }

    private static PacketByteBuf encode(IPacket packet) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)) {
            String key = null;
            for (Packet p : packetRegistry) if (packet.getClass() == p.clazz) {
                key = p.key; break;
            }
            if (key == null) return null;

            byte[] data = packet.encode();
            dos.writeUTF(key);
            dos.writeInt(data.length);
            dos.write(data);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeByteArray(baos.toByteArray());
            return buf;
        } catch (IOException e) {
            AmaCarpet.LOGGER.error(e);
            return null;
        }
    }

    public static void handleC2SPacket(AmaCarpetPacketPayload payload, ServerPlayNetworkHandler handler) {
        AmaCarpet.LOGGER.info("received c2s packet");
        AmaCarpetServer.minecraft_server.execute(() -> {
            IPacket packet = decode(payload.content);
            if (packet == null) return;
            AmaCarpet.LOGGER.info("call server packet handler asap");
            packet.onServer(handler.player);
        });

    }

    public static void handleS2CPacket(AmaCarpetPacketPayload payload) {
        AmaCarpet.LOGGER.info("received s2c packet");
        MinecraftClient.getInstance().execute(() -> {
            IPacket packet = decode(payload.content);
            if (packet == null) return;
            AmaCarpet.LOGGER.info("call client packet handler asap");
            packet.onClient();
        });
    }

    public static void sendC2S(IPacket packet) {
        AmaCarpetPacketPayload packetPayload = new AmaCarpetPacketPayload(Objects.requireNonNull(encode(packet)).readByteArray());
        packetPayload.sendC2S();
    }

    public static void sendS2C(IPacket packet, ServerPlayerEntity player) {
        AmaCarpetPacketPayload packetPayload = new AmaCarpetPacketPayload(Objects.requireNonNull(encode(packet)).readByteArray());
        AmaCarpet.LOGGER.info("sending custom packet s2c");
        packetPayload.sendS2C(player);
    }
}
