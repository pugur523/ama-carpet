// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.spawn;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlatLevelSource.class)
public class MixinFlatLevelSource {
    @Inject(method = "spawnOriginalMobs(Lnet/minecraft/server/level/WorldGenRegion;)V", at = @At("HEAD"), cancellable = true)
    private void onChunkGenAnimalSpawn(WorldGenRegion par1, CallbackInfo ci) {
        if (AmaCarpetSettings.disableAnimalSpawnOnChunkGen) ci.cancel();
    }
}
