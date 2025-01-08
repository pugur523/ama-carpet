package org.amateras_smp.amacarpet.mixins.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;
import org.amateras_smp.amacarpet.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLoginNetworkHandler.class)
public class MixinServerLoginNetworkHandler {
    @Unique
    private ServerPlayerEntity player;
    @Final
    @Shadow
    MinecraftServer server;
    @Shadow
    GameProfile profile;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        System.out.println("debug: onTick");
        if (profile == null) return;
        if (player == null) player = server.getPlayerManager().getPlayer(profile.getName());
        if (player == null) return;
        if (!PlayerUtil.amaClientCheck(player)) {
            ci.cancel();
        }
    }
}
