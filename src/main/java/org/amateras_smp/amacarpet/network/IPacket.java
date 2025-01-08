package org.amateras_smp.amacarpet.network;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class IPacket {

    public byte[] encode() {
        return new byte[0];
    }

    public abstract void onServer(ServerPlayerEntity player);

    public abstract void onClient();
}
