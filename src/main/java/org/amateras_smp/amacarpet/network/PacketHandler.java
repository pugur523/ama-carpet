// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacketHandler {
    private static final List<Packet> packetRegistry = new ArrayList<>();

    private static class Packet {
        private final String key;
        private final Class<? extends IPacket> clazz;

        Packet(String key, Class<? extends IPacket> clazz) {
            this.key = key;
            this.clazz = clazz;
        }
    }

    static {
        packetRegistry.add(new Packet("handshake", HandshakePacket.class));
    }

    private static IPacket decode(byte[] raw) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(raw); DataInputStream dis = new DataInputStream(byteArrayInputStream)) {
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

    private static byte[] encode(IPacket packet) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(byteArrayOutputStream)) {
            String key = null;
            for (Packet p : packetRegistry) if (packet.getClass() == p.clazz) {
                key = p.key; break;
            }
            if (key == null) return null;

            byte[] data = packet.encode();
            dos.writeUTF(key);
            dos.writeInt(data.length);
            dos.write(data);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            AmaCarpet.LOGGER.error(e);
            return null;
        }
    }

    public static void handleC2S(byte[] data, ServerPlayer player) {
        AmaCarpet.LOGGER.debug("handling c2s packet");
        IPacket packet = decode(data);
        if (packet == null) return;
        packet.onServer(player);
    }

    public static void handleS2C(byte[] data) {
        AmaCarpet.LOGGER.debug("handling s2c packet");
        Minecraft.getInstance().execute(() -> {
            AmaCarpet.LOGGER.debug("handling s2c executed by client thread");
            IPacket packet = decode(data);
            if (packet == null) return;
            packet.onClient();
        });
    }

    public static void sendC2S(IPacket packet) {
        AmaCarpet.LOGGER.debug("sending c2s packet");
        AmaCarpetPayload packetPayload = new AmaCarpetPayload(encode(packet));
        packetPayload.sendC2S();
    }

    public static void sendS2C(IPacket packet, ServerPlayer player) {
        AmaCarpet.LOGGER.debug("sending s2c packet");
        AmaCarpetPayload packetPayload = new AmaCarpetPayload(encode(packet));
        packetPayload.sendS2C(player);
    }
}
