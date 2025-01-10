package org.amateras_smp.amacarpet.mixins.ticket;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TheEndGatewayBlockEntity.class)
public class MixinTheEndGatewayBlockEntity {
    //#if MC < 12100
    // this was implemented in 1.21 vanilla
    @Inject
    (
            method = "teleportEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;teleportToWithTicket(DDD)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void onEntityTeleport(Level level, BlockPos blockPos, BlockState blockState, Entity entity, TheEndGatewayBlockEntity theEndGatewayBlockEntity, CallbackInfo ci, @Local(ordinal = 1) BlockPos blockPos2) {
        if (!AmaCarpetSettings.endGatewayChunkLoad) return;
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockPos2), 3, blockPos2);
        }
    }
    //#endif
}
