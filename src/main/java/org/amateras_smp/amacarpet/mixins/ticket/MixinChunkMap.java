package org.amateras_smp.amacarpet.mixins.ticket;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.ReportedException;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerLevel;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public abstract class MixinChunkMap {

    @Shadow @Final
    ServerLevel level;

    @Inject(method = "debugFuturesAndCreateReportedException", at = @At("HEAD"))
    private void onServerCrash(IllegalStateException illegalStateException, String string, CallbackInfoReturnable<ReportedException> cir) {
        ChunkTicketUtil.addAllTickets(level);
    }
}
