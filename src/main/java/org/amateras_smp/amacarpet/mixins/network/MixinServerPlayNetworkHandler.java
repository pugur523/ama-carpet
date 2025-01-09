package org.amateras_smp.amacarpet.mixins.network;

import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.AmaCarpetPacketPayload;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if 12002 <= MC && MC < 12005
//$$ import net.minecraft.server.network.ServerCommonNetworkHandler;
//#endif

@Mixin(
        //#if 12002 <= MC && MC < 12005
        //$$ ServerCommonNetworkHandler.class
        //#else
        ServerPlayNetworkHandler.class
        //#endif
)
public class MixinServerPlayNetworkHandler {
    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        AmaCarpet.LOGGER.debug("onCustomPayload(c2s)");

        //#if MC >= 12002
        //$$ if (packet.payload() instanceof AmaCarpetPacketPayload amaCustomPayload && (Object)this instanceof ServerPlayNetworkHandler self) {
        //$$    PacketHandler.handleC2SPacket(amaCustomPayload, self);
        //$$    ci.cancel();
        //$$ }
        //#else
        Identifier channel = ((CustomPayloadC2SPacketAccessor)packet).getChannel();
        if (AmaCarpetPacketPayload.identifier.equals(channel)) {
            AmaCarpetPacketPayload amaCustomPayload = new AmaCarpetPacketPayload(packet.getData().readByteArray());
            PacketHandler.handleC2SPacket(amaCustomPayload, (ServerPlayNetworkHandler)(Object)this);
            ci.cancel();
        }
        //#endif
    }
}
