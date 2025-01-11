// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.network;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class MixinPlayerList {
    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    private void onPlayerConnect(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        if (AmaCarpetServer.MINECRAFT_SERVER.isSingleplayer()) return;
        PlayerUtil.addShouldAuthPlayer(serverPlayer.getName().getString());
    }
}
