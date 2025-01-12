package org.amateras_smp.amacarpet.client.mixins.addon.tweakeroo;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.EnableSpecifiedFeaturePacket;
import org.amateras_smp.amacarpet.utils.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.tweakeroo))
@Mixin(value = FeatureToggle.class, remap = false)
public class MixinFeatureToggle {
    @Inject(method = "setBooleanValue(Z)V", at = @At("HEAD"))
    private void onSetBooleanValue(boolean value, CallbackInfo ci) {
        // FeatureToggle overrides setBooleanValue() so this is needed.
        AmaCarpet.LOGGER.debug("setBooleanValue to {} in FeatureToggle", value ? "true" : "false");
        if (!value) return;
        FeatureToggle self = (FeatureToggle)(Object) this;
        String changed = self.getName();
        String sneak_changed = StringUtil.camelToSneak(changed);
        for (String feature : ClientModUtil.tweakerooFeaturesWatchList) {
            if (sneak_changed.equals("tweak_" + feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
    }
}
