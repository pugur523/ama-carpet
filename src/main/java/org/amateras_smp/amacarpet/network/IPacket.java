// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3. See the LICENSE file for details.

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