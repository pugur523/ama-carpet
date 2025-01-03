package org.amateras_smp.amacarpet.mixins.ticket;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "prepareStartRegion", at = @At("HEAD"))
    private void onPrepareStartRegion(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) throws IOException {
        ChunkTicketUtil.setPath((MinecraftServer) (Object) this);
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.load((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("TAIL"))
    private void onServerShutdown(CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.save();
    }

}
