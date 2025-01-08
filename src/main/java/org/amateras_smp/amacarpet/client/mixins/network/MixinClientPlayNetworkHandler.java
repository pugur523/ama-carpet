package org.amateras_smp.amacarpet.client.mixins.network;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.mixins.network.CustomPayloadS2CPacketAccessor;
import org.amateras_smp.amacarpet.network.AmaCarpetPacketPayload;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12002
//$$ import net.minecraft.client.network.ClientCommonNetworkHandler;
//$$ import net.minecraft.client.MinecraftClient;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#endif

@Mixin(
        //#if MC >= 12002
        //$$ ClientCommonNetworkHandler.class
        //#else
        ClientPlayNetworkHandler.class
        //#endif
)
public abstract class MixinClientPlayNetworkHandler {
    @Inject(
            //#if MC >= 12002
            //$$ method = "onCustomPayload(Lnet/minecraft/network/packet/s2c/common/CustomPayloadS2CPacket;)V",
            //#else
            method = "onCustomPayload",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci)
    {
        //#if MC >= 12002
        //$$ if (packet.payload() instanceof AmaCarpetPacketPayload amaCustomPayload) {
        //$$    PacketHandler.handleS2CPacket(amaCustomPayload);
        //$$    ci.cancel();
        //$$ }
        //#else
        Identifier channel = ((CustomPayloadS2CPacketAccessor)packet).getChannel();
        if (AmaCarpetPacketPayload.identifier.equals(channel))
        {
            AmaCarpetPacketPayload amaCustomPayload = new AmaCarpetPacketPayload(packet.getData().readByteArray());
            PacketHandler.handleS2CPacket(amaCustomPayload);
            ci.cancel();
        }
        //#endif
    }
}
