package org.amateras_smp.amacarpet.client.mixins.container;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.network.packet.C2S.ContainerQueryC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Unique
    private BlockPos lastLookedAtBlock = null;

    @Inject(method = "tick", at = @At("TAIL"))
    public void onClientTick(CallbackInfo ci) {
        if (!AmaCarpetSettings.serverContainerSync) return;
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        HitResult hitResult = player.raycast(5.0D, 0.0F, false);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos currentBlock = blockHitResult.getBlockPos();

            if (lastLookedAtBlock == null || !lastLookedAtBlock.equals(currentBlock)) {
                lastLookedAtBlock = currentBlock;

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(currentBlock);
                ClientPlayNetworking.send(ContainerQueryC2SPacket.CONTAINER_QUERY_PACKET, buf);
            }
        }
    }
}
