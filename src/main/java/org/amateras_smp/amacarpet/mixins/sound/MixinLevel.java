// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.sound;

import net.minecraft.world.level.Level;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class MixinLevel {

    @Inject(method = "playSound*", at = @At("HEAD"), cancellable = true)
    public void onPlaySound(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound1(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playLocalSound*", at = @At("HEAD"), cancellable = true)
    public void onPlaySound2(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }
}
