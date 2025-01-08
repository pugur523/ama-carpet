package org.amateras_smp.amacarpet.mixins.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class MixinServerLoginNetworkHandler {
    //#if MC <= 12002
    @Shadow
    GameProfile profile;
    //#else
    //$$ @Shadow
    //$$ String profileName;
    //#endif

    @Inject(method = "onHello", at = @At(value = "RETURN"))
    private void onHello(CallbackInfo ci) {
        AmaCarpetServer.LOGGER.info("debug: on hello");
        //#if MC <= 12002
        if (profile == null) return;
        String profileName = profile.getName();
        //#endif
        if (profileName == null || profileName.isBlank()) return;
        PlayerUtil.addShouldAuthPlayer(profileName);
    }
}
