package org.amateras_smp.amacarpet.mixins.syncmatica;

import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.communication.ClientCommunicationManager;
import ch.endte.syncmatica.communication.CommunicationManager;
import ch.endte.syncmatica.communication.ExchangeTarget;
import ch.endte.syncmatica.communication.PacketType;
import ch.endte.syncmatica.communication.exchange.Exchange;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ClientCommunicationManager.class)
public abstract class MixinClientCommunicationManager extends CommunicationManager {
    @Inject(method = "handle(Lch/endte/syncmatica/communication/ExchangeTarget;Lnet/minecraft/util/Identifier;Lnet/minecraft/network/PacketByteBuf;)V", at = @At("TAIL"))
    private void onRemovePlacement(ExchangeTarget source, Identifier id, PacketByteBuf packetBuf, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifyLitematicShared || !id.equals(PacketType.REMOVE_SYNCMATIC.identifier)) return;
        UUID placementId = packetBuf.readUuid();
        ServerPlacement placement = context.getSyncmaticManager().getPlacement(placementId);
        Text message = Text.literal(source.serverPlayNetworkHandler.player.getName().getString()).formatted(Formatting.RED).append(
                Text.literal(" removed a shared litematic. \nPlacement name : ").formatted(Formatting.WHITE)).append(
                Text.literal(placement.getName()).formatted(Formatting.YELLOW));
        AmaCarpetServer.minecraft_server.getPlayerManager().broadcast(message, false);
    }
}
