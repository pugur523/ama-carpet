// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.carpet;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.SettingsManager;
import net.minecraft.commands.CommandSourceStack;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.ModStatusQueryPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SettingsManager.class)
public class MixinSettingsManager {
    @Inject(method = "setRule(Lnet/minecraft/commands/CommandSourceStack;Lcarpet/api/settings/CarpetRule;Ljava/lang/String;)I", at = @At("RETURN"))
    private void onCarpetSettingsChanged(CommandSourceStack source, CarpetRule<?> rule, String newValue, CallbackInfoReturnable<Integer> cir) {
        if (rule.name().equals("cheatRestriction") && newValue.equals("true")) {
            AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().getPlayers().forEach(
                    (serverPlayer -> PacketHandler.sendS2C(new ModStatusQueryPacket(), serverPlayer))
            );
        }
    }
}
