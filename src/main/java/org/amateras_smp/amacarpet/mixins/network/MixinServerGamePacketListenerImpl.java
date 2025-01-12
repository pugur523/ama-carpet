package org.amateras_smp.amacarpet.mixins.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if 12002 <= MC && MC < 12005
//$$ import org.amateras_smp.amacarpet.AmaCarpetServer;
//$$ import net.minecraft.server.network.ServerCommonPacketListenerImpl;
//#endif


@Mixin(
        //#if 12002 <= MC && MC < 12005
        //$$ ServerCommonPacketListenerImpl.class
        //#else
        ServerGamePacketListenerImpl.class
        //#endif
)
public abstract class MixinServerGamePacketListenerImpl {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onCustomPayload$AMA(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        //#if 12002 <= MC && MC < 12005
        //$$ ServerCommonPacketListenerImpl self = (ServerCommonPacketListenerImpl)(Object) this;
        //#else
        ServerGamePacketListenerImpl self = (ServerGamePacketListenerImpl)(Object) this;
        //#endif

        //#if MC >= 12002
        //$$ AmaCarpet.LOGGER.debug("onCustomC2SPayload, payload : {}", packet.payload());
        //$$ if (packet.payload() instanceof AmaCarpetPayload amaPayload) {
        //$$    AmaCarpet.LOGGER.debug("received C2S AmaCarpetPayload : {}", amaPayload);
        //$$
        //#if MC < 12005
        //$$    PacketHandler.handleC2S(amaPayload.content(), AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().getPlayer(self.getOwner().getId()));
        //#else
        //$$    PacketHandler.handleC2S(amaPayload.content(), self.player);
        //#endif
        //$$    ci.cancel();
        //$$ }
        //#else

        ResourceLocation channel = packet.getIdentifier();
        FriendlyByteBuf data = packet.getData();

        if (AmaCarpetPayload.identifier.equals(channel)) {
            if (data.readableBytes() > 0) {
                byte[] payload = data.readByteArray();
                AmaCarpet.LOGGER.debug("onCustomC2SPayload, channel : {}, data : {}", channel, payload);
                PacketHandler.handleC2S(payload, self.player);
                ci.cancel();
            } else {
                AmaCarpet.LOGGER.warn("Received empty or insufficient data on channel: {}", channel);
            }
        }
        //#endif
    }
}
