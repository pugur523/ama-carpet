package org.amateras_smp.amacarpet.mixins.syncmatica;

import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.SyncmaticManager;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.syncmatica))
@Mixin(value = SyncmaticManager.class, remap = false)
public class MixinSyncmaticManager {
    @Inject(method = "addPlacement", at = @At("TAIL"))
    private void onAddPlacement(ServerPlacement placement, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifyLitematicShared) return;
        Component message = Component.literal(placement.getOwner().getName()).withStyle(ChatFormatting.GREEN).append(
                Component.literal(" shared a litematic! \nPlacement name : ").withStyle(ChatFormatting.WHITE)).append(
                Component.literal(placement.getName()).withStyle(ChatFormatting.YELLOW)).append(
                Component.literal("\nDimension : " + placement.getDimension()).withStyle(ChatFormatting.WHITE));
        AmaCarpetServer.LOGGER.info(message.getString());
        AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
    }
}
