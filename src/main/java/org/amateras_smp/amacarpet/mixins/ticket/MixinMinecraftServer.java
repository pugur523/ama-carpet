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
import java.util.function.BooleanSupplier;

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

    @Inject(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;"))
    public void onCrash(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.save();
    }
}
