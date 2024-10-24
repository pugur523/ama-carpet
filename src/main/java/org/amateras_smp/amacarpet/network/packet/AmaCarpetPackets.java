package org.amateras_smp.amacarpet.network.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.amateras_smp.amacarpet.utils.ServerContainerUtil;

import static org.amateras_smp.amacarpet.network.packet.C2S.ContainerQueryC2SPacket.CONTAINER_QUERY_PACKET;
import static org.amateras_smp.amacarpet.network.packet.S2C.ServerContainerS2CPacket.SERVER_CONTAINER_PACKET;

public class AmaCarpetPackets {
    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SERVER_CONTAINER_PACKET, (client, handler, buf, responseSender) -> ServerContainerUtil.onResponse(buf));
    }

    public static void registerS2CPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CONTAINER_QUERY_PACKET, (server, player, handler, buf, responseSender) -> ServerContainerUtil.onQuery(server, player, buf, responseSender));

    }
}
