// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network.packets;

import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.network.IPacket;
import org.amateras_smp.amacarpet.utils.PlayerUtil;

import java.nio.charset.StandardCharsets;

public class EnableSpecifiedFeaturePacket extends IPacket {
    private final String featureName;

    public EnableSpecifiedFeaturePacket(String featureName) {
        this.featureName = featureName;
    }
    
    public EnableSpecifiedFeaturePacket(byte[] bytes) {
        this.featureName = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] encode() {
        return this.featureName.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void onServer(ServerPlayer player) {
        if (!AmaCarpetSettings.cheatRestriction) return;
        if (CheatRestrictionConfig.getInstance().get(featureName)) {
            PlayerUtil.onCatchCheater(player, featureName);
        }
    }
}
