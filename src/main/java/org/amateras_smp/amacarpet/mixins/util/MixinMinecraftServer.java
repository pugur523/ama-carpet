package org.amateras_smp.amacarpet.mixins.util;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        // on tick utility
    }
}
