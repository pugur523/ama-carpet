// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.network;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12002
//$$ import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
//#endif

@Mixin(
        //#if MC >= 12002
        //$$ ClientCommonPacketListenerImpl.class
        //#else
        ClientPacketListener.class
        //#endif
)
public class MixinClientPacketListener {
    @Unique
    private final String targetMethod =
    //#if MC < 12002
    "handleCustomPayload";
    //#else
    //$$ "handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V";
    //#endif

    @Inject(method = targetMethod, at = @At("HEAD"), cancellable = true)
    private void onCustomPayload$AMA(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        //#if MC >= 12002
        //$$ if (packet.payload() instanceof AmaCarpetPayload amaPayload) {
        //$$    AmaCarpet.LOGGER.debug("received S2C AmaCarpetPayload : {}", amaPayload);
        //$$    PacketHandler.handleS2C(amaPayload.content());
        //$$ 	ci.cancel();
        //$$ }
        //#else
        ResourceLocation channel = packet.getIdentifier();
        FriendlyByteBuf data = packet.getData();

        if (AmaCarpetPayload.identifier.equals(channel)) {
            if (data.readableBytes() > 0) {
                byte[] payload = data.readByteArray();
                AmaCarpet.LOGGER.debug("onCustomS2CPayload, channel : {}, data : {}", channel, payload);
                PacketHandler.handleS2C(payload);
                ci.cancel();
            } else {
                AmaCarpet.LOGGER.warn("Received empty or insufficient data on channel: {}", channel);
            }
        }
        //#endif
    }
}
