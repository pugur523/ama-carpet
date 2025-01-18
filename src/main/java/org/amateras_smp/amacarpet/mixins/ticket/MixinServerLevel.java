// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.ticket;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;

import java.io.IOException;

import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel {

    //#if MC < 12100
    // this was implemented in 1.21 so is not needed in 1.21 or above
    @Unique
    private void issuePortalTicket(Entity entity) {
        if (!AmaCarpetSettings.endPortalChunkLoad) return;
        try (ServerLevel level = (ServerLevel) (Object) this) {
            if (level.dimension() == ServerLevel.END) {
                BlockPos pos = entity.getOnPos();
                level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(pos), 3, pos);
            }
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("Error in MixinServerLevel.issuePortalTicket() : ", e);
        }
    }

    @Inject(method = "addDuringPortalTeleport", at = @At("TAIL"))
    private void onPlayerDimensionChanged(ServerPlayer serverPlayer, CallbackInfo ci) {
        issuePortalTicket(serverPlayer);
    }

    @Inject(method = "addDuringTeleport", at = @At("TAIL"))
    private void onEntityDimensionChanged(Entity entity, CallbackInfo ci) {
        issuePortalTicket(entity);
    }
    //#endif
}
