package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class MixinServerWorld {
    @Inject(method = "createEndSpawnPlatform",
            at = @At("TAIL")
    )
    private static void onCreateEndSpawnPlatform(ServerWorld world, CallbackInfo ci, @Local BlockPos pos) {
        if (!AmaCarpetSettings.endPortalChunkLoad) return;
        world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 3, pos);
    }
}
