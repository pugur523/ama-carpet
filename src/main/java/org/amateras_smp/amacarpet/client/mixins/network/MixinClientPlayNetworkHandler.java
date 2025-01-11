// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.network;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPlayNetworkHandler {
    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void sendHandshakePacket(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo ci) {
        HandshakePacket handshakePacket = new HandshakePacket(AmaCarpet.getVersion());
        PacketHandler.sendC2S(handshakePacket);
    }
}
