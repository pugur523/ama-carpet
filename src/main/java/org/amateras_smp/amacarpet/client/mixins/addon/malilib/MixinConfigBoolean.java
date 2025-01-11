// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.addon.malilib;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.EnableSpecifiedFeaturePacket;
import org.amateras_smp.amacarpet.utils.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ConfigBoolean.class, remap = false)
public class MixinConfigBoolean {
    @Inject(method = "setBooleanValue", at = @At("HEAD"))
    private void onEnableFeature(boolean value, CallbackInfo ci) {
        if (!value) return;
        ConfigBoolean self = (ConfigBoolean)(Object) this;
        String changed = self.getName();
        String sneak_changed = StringUtil.camelToSneak(changed);
        for (String feature : ClientModUtil.tweakerooFeaturesWatchList) {
            if (sneak_changed.equals("tweak_" + feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
        for (String feature : ClientModUtil.tweakerooYeetsWatchList) {
            if (sneak_changed.equals("disable_" + feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
        for (String feature : ClientModUtil.tweakermoreWatchList) {
            if (sneak_changed.equals(feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
        for (String feature : ClientModUtil.litematicaWatchList) {
            if (sneak_changed.equals(feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
    }
}
