package org.amateras_smp.amacarpet.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.network.packets.HandShake;

import static org.amateras_smp.amacarpet.AmaCarpet.MOD_ID;

public class RegisterPackets {
    public static Identifier HANDSHAKE_S2C = new Identifier(MOD_ID, "handshake_s2c");
    public static Identifier HANDSHAKE_C2S = new Identifier(MOD_ID, "handshake_c2s");

    public static void registerServer() {
        //#if MC < 12004
        ServerPlayNetworking.registerGlobalReceiver(HANDSHAKE_C2S, HandShake::serverHandler);
        //#endif
    }

    public static void registerClient() {
        //#if MC < 12004
        ClientPlayNetworking.registerGlobalReceiver(HANDSHAKE_S2C, HandShake::clientHandler);
        //#endif
    }
}
