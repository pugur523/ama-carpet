package org.amateras_smp.amacarpet.mixins.sound;

import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class MixinWorld {

    //#if MC >= 11900

    @Inject(method = "playSound(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound1(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFJ)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound2(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playSoundAtBlockCenter(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound3(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound4(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    @Inject(method = "playSoundFromEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound5(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }

    //#endif

    @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound6(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }


    @Inject(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V", at = @At("HEAD"), cancellable = true)
    public void onPlaySound7(CallbackInfo ci) {
        if (AmaCarpetSettings.disableSoundEngine) ci.cancel();
    }
}
