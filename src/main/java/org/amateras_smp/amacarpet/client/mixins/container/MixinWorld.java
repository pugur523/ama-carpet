package org.amateras_smp.amacarpet.client.mixins.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.client.feature.ServerContainerCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(World.class)
public class MixinWorld {
    @Inject(method = "getBlockEntity", at = @At("TAIL"), cancellable = true)
    private void onGetBlockEntity(BlockPos pos, CallbackInfoReturnable<BlockEntity> cir) {
        if (!AmaCarpetSettings.serverContainerSync) return;

        BlockEntity blockEntity = cir.getReturnValue();
        if (blockEntity == null) return;

        NbtCompound nbt = ServerContainerCache.getInstance().get(pos);
        if (nbt != null){
            blockEntity.readNbt(nbt);
        }

        // override block entity nbt data
        cir.setReturnValue(blockEntity);
    }
}
