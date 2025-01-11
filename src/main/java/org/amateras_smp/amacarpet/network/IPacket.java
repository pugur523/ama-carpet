// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network;


import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;

public abstract class IPacket {
    public IPacket() {
        // noop
    }

    public IPacket(byte[] bytes) {
        // noop
    }

    public byte[] encode() {
        return new byte[0];
    }

    public void onServer(ServerPlayer player) {
        AmaCarpet.LOGGER.warn("[AmaCarpet] unhandled packet received from client.");
    }

    public void onClient() {
        AmaCarpet.LOGGER.warn("[AmaCarpet] unhandled packet received from server.");
    }
}