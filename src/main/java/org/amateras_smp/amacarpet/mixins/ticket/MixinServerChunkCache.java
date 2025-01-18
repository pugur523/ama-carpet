// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.ticket;

import net.minecraft.server.level.*;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkCache.class)
public class MixinServerChunkCache {
    @Shadow
    @Final
    ServerLevel level;

    @Inject(method = "removeTicketsOnClosing", at = @At("HEAD"))
    private void onServerClose(CallbackInfo ci) {
        ChunkTicketUtil.addAllTickets(level);
    }
}
