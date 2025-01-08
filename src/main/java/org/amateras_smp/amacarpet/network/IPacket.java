package org.amateras_smp.amacarpet.network;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class IPacket {

    public byte[] encode() {
        return new byte[0];
    }

    public void onServer(ServerPlayerEntity player) {
        // noop
    }

    public void onClient() {
        // noop
    }
}
