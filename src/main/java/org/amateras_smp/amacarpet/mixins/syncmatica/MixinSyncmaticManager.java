package org.amateras_smp.amacarpet.mixins.syncmatica;

import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.SyncmaticManager;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
    @Inject(method = "addPlacement(Lch/endte/syncmatica/ServerPlacement;)V", at = @At("TAIL"))
    private void onAddPlacement(ServerPlacement placement, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifySyncmaticShared) return;
        Text message = Text.literal(placement.getOwner().getName()).formatted(Formatting.GREEN).append(
                Text.literal(" shared a syncmatic! \nPlacement name : ").formatted(Formatting.WHITE)).append(
                Text.literal(placement.getName()).formatted(Formatting.YELLOW)).append(
                Text.literal("\nDimension : " + placement.getDimension()).formatted(Formatting.WHITE));
        AmaCarpetServer.minecraft_server.getPlayerManager().broadcast(message, false);
    }

    @Inject(method = "removePlacement(Lch/endte/syncmatica/ServerPlacement;)V", at = @At("TAIL"))
    private void onRemovePlacement(ServerPlacement placement, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifySyncmaticShared) return;
        Text message = Text.literal(placement.getOwner().getName()).formatted(Formatting.RED).append(
                Text.literal(" removed a syncmatic. \nPlacement name : ").formatted(Formatting.WHITE)).append(
                Text.literal(placement.getName()).formatted(Formatting.YELLOW));
        AmaCarpetServer.minecraft_server.getPlayerManager().broadcast(message, false);
    }
}
