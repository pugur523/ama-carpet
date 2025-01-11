// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network.packets;

import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.network.IPacket;
import org.amateras_smp.amacarpet.utils.PlayerUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ModStatusResponsePacket extends IPacket {
    private final HashMap<String, Boolean> map;

    public ModStatusResponsePacket(HashMap<String, Boolean> map) {
        this.map = map;
    }

    public ModStatusResponsePacket(byte[] bytes) {
        this.map = new HashMap<>();
        try (DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes))) {
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                String key = dataInputStream.readUTF();
                Boolean value = dataInputStream.readBoolean();
                map.put(key, value);
            }
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("couldn't decode mod status response packet : ", e);
        }
    }

    @Override
    public byte[] encode() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(map.size());

            for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey());
                dataOutputStream.writeBoolean(entry.getValue());
            }

            return byteArrayOutputStream.toByteArray();


        } catch (IOException e) {
            AmaCarpet.LOGGER.error("couldn't encode mod status response packet : ", e);
        }
        return new byte[0];
    }

    @Override
    public void onServer(ServerPlayer player) {
        if (!AmaCarpetSettings.cheatRestriction) return;
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue() && !CheatRestrictionConfig.getInstance().getStringValue(entry.getKey()).equals("true")) {
                PlayerUtil.onCatchCheater(player, entry.getKey());
            }
        }
    }
}
