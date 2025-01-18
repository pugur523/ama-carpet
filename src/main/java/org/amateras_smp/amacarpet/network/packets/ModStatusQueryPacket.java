// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network.packets;

import org.amateras_smp.amacarpet.network.IPacket;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;

import java.util.HashMap;

public class ModStatusQueryPacket extends IPacket {
    public ModStatusQueryPacket() {
        // noop
    }

    public ModStatusQueryPacket(byte[] bytes) {
        // noop
    }

    @Override
    public void onClient() {
        HashMap<String, Boolean> config = ClientModUtil.createConfigDataMap();
        ModStatusResponsePacket packet = new ModStatusResponsePacket(config);
        PacketHandler.sendC2S(packet);
    }
}
