package org.amateras_smp.amacarpet.network;


import net.minecraft.server.level.ServerPlayer;

public abstract class IPacket {
    public byte[] encode() {
        return new byte[0];
    }

    public void onServer(ServerPlayer player) {
        // noop
    }

    public void onClient() {
        // noop
    }
}