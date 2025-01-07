package org.amateras_smp.amacarpet.mixins.syncmatica;

import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.communication.*;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Restriction(require = @Condition(AmaCarpet.ModIds.syncmatica))
@Mixin(ServerCommunicationManager.class)
public abstract class MixinServerCommunicationManager extends CommunicationManager {
    @Inject(method = "handle", at = @At("TAIL"))
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
