package org.amateras_smp.amacarpet.client.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void onJoinWorld(CallbackInfo ci) {
        AmaCarpetClient.isConnected = true;
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconnect(Screen screen, CallbackInfo ci) {
        AmaCarpetClient.isConnected = false;
    }
}