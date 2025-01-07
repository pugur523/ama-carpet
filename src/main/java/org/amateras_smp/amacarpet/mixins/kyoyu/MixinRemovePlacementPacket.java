package org.amateras_smp.amacarpet.mixins.kyoyu;

import com.llamalad7.mixinextras.sugar.Local;
import com.vulpeus.kyoyu.net.packets.RemovePlacementPacket;
import com.vulpeus.kyoyu.placement.KyoyuPlacement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RemovePlacementPacket.class)
public class MixinRemovePlacementPacket {
    @Inject(method = "onServer(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void onRemoveKyoyuPlacement(ServerPlayerEntity serverPlayer, CallbackInfo ci, @Local KyoyuPlacement kyoyuPlacement) {
        if (!AmaCarpetSettings.notifyLitematicShared || kyoyuPlacement == null) return;
        Text message = Text.literal(serverPlayer.getName().getString()).formatted(Formatting.RED).append(
                Text.literal(" removed a shared litematic. \nPlacement name : ").formatted(Formatting.WHITE)).append(
                Text.literal(kyoyuPlacement.getName()).formatted(Formatting.YELLOW));
        AmaCarpetServer.minecraft_server.getPlayerManager().broadcast(message, false);
    }
}
