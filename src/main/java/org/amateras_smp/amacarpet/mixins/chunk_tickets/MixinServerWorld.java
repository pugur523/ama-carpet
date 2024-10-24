package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class MixinServerWorld {

    //#if MC < 12100
    // this was implemented in 1.21 so is not needed in 1.21 or above
    @Unique
    private void issuePortalTicket(Entity entity) {
        if (!AmaCarpetSettings.endPortalChunkLoad) return;
        ServerWorld world = (ServerWorld) (Object) this;
        if (world.getRegistryKey() == ServerWorld.END) {
            BlockPos pos = entity.getBlockPos();
            world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 3, pos);
        }
    }

    @Inject(method = "onPlayerChangeDimension", at = @At("TAIL"))
    private void onPlayerDimensionChanged(ServerPlayerEntity player, CallbackInfo ci) {
        issuePortalTicket(player);
    }

    @Inject(method = "onDimensionChanged", at = @At("TAIL"))
    private void onEntityDimensionChanged(Entity entity, CallbackInfo ci) {
        issuePortalTicket(entity);
    }
    //#endif
}
