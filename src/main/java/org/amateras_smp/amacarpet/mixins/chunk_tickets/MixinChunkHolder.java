package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import net.minecraft.server.world.*;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12100
//$$ import org.spongepowered.asm.mixin.Unique;
//#endif

import java.util.concurrent.Executor;

@Mixin(ChunkHolder.class)
public abstract class MixinChunkHolder {
    //#if MC >= 12100
    //$$ @Unique ChunkPos pos;
    //$$ public MixinChunkHolder(ChunkPos pos) {
    //$$     this.pos = pos;
    //$$ }
    //#else
    @Shadow @Final ChunkPos pos;
    //#endif

    //#if MC >= 12100
    //$$ @Inject(method = "updateFutures", at = @At("RETURN"))
    //#else
    @Inject(method = "tick", at = @At("RETURN"))
    //#endif
    private void onTick(ThreadedAnvilChunkStorage chunkStorage, Executor executor, CallbackInfo ci) {

    }
}
