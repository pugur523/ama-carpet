// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.syncmatica;

import carpet.utils.Translations;
import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.communication.*;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.UUID;

//#if MC < 12004
import net.minecraft.resources.ResourceLocation;
//#else
//$$ import ch.endte.syncmatica.network.PacketType;
//#endif

@Restriction(require = @Condition(AmaCarpet.ModIds.syncmatica))
@Mixin(ServerCommunicationManager.class)
public abstract class MixinServerCommunicationManager extends CommunicationManager {
    @Inject(method = "handle", at = @At("HEAD"))
    //#if MC < 12004
    private void onRemovePlacement(ExchangeTarget source, ResourceLocation id, FriendlyByteBuf packetBuf, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifySchematicShare || !id.equals(PacketType.REMOVE_SYNCMATIC.identifier) || AmaCarpet.kIsClient) return;
    //#else
    //$$ private void onRemovePlacement(ExchangeTarget source, PacketType type, FriendlyByteBuf packetBuf, CallbackInfo ci) {
    //$$     if (!AmaCarpetSettings.notifySchematicShare || !type.equals(PacketType.REMOVE_SYNCMATIC)) return;
    //#endif
        FriendlyByteBuf copiedBuf = new FriendlyByteBuf(packetBuf.copy());
        UUID placementId = copiedBuf.readUUID();
        ServerPlacement placement = context.getSyncmaticManager().getPlacement(placementId);
        Component message = Component.literal(source.serverPlayNetworkHandler.player.getName().getString()).withStyle(ChatFormatting.RED).append(
                Component.literal(Translations.tr("ama.message.schematic.unshared")).withStyle(ChatFormatting.WHITE)).append(
                Component.literal(placement.getName()).withStyle(ChatFormatting.YELLOW));
        AmaCarpetServer.LOGGER.info(message.getString());
        AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
    }
}
