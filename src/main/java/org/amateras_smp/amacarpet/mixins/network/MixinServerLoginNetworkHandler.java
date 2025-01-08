package org.amateras_smp.amacarpet.mixins.network;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class MixinServerLoginNetworkHandler {
    //#if MC <= 12002
    @Shadow
    GameProfile profile;
    //#endif

    @Unique
    boolean addedPlayer = false;

    //#if MC >= 12002
    //$$ @Inject(method = "sendSuccessPacket(Lcom/mojang/authlib/GameProfile;)V", at = @At("HEAD"))
    //$$ private void onAccept(GameProfile profile, CallbackInfo ci) {
    //#else
    @Inject(method = "acceptPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerLoginNetworkHandler;addToServer(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
    private void onAccept(CallbackInfo ci, @Local(ordinal = 1) ServerPlayerEntity serverPlayerEntity2) {
    //#endif
        AmaCarpetServer.LOGGER.info("debug: on accept");
        if (profile == null) return;
        if (!addedPlayer) {
            PlayerUtil.waitingPlayers.add((ServerLoginNetworkHandler)(Object)this);
            addedPlayer = true;
        }
        PlayerUtil.amaClientCheckCanJoin((ServerLoginNetworkHandler)(Object)this);
    }
}
