package org.amateras_smp.amacarpet.client.mixins.network;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandlerForHandshake {
    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void sendHandshakePacket(GameJoinS2CPacket packet, CallbackInfo ci) {
        AmaCarpetClient.LOGGER.info("[AmaCarpetClientDebug] onHello() send hand shake packet");
        HandshakePacket handshakePacket = new HandshakePacket(AmaCarpet.getVersion());
        PacketHandler.sendC2S(handshakePacket);
    }
}
