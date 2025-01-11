// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.ticket;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "prepareLevels", at = @At("HEAD"))
    private void onPrepareLevels(ChunkProgressListener chunkProgressListener, CallbackInfo ci) throws IOException {
        ChunkTicketUtil.setPath((MinecraftServer) (Object) this);
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.load((MinecraftServer) (Object) this);
    }

    @Inject(method = "stopServer", at = @At("TAIL"))
    private void onServerStop(CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.save();
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;constructOrExtractCrashReport(Ljava/lang/Throwable;)Lnet/minecraft/CrashReport;"))
    public void onCrash(CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketUtil.save();
    }
}
