// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.kyoyu;

import carpet.utils.Translations;
import com.vulpeus.kyoyu.placement.KyoyuPlacement;
import com.vulpeus.kyoyu.placement.KyoyuRegion;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Restriction(require = @Condition(AmaCarpet.ModIds.kyoyu))
@Mixin(value = KyoyuPlacement.class, remap = false)
public class MixinKyoyuPlacement {
    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void onAddKyoyuPlacement(UUID uuid, KyoyuRegion region, List<KyoyuRegion> subRegions, String ownerName, String updaterName, File file, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifySchematicShare || AmaCarpet.kIsClient) return;
        KyoyuPlacement placement = (KyoyuPlacement)(Object) this;
        Component message = Component.literal(ownerName).withStyle(ChatFormatting.GREEN).append(
                Component.literal(Translations.tr("ama.message.schematic.shared")).withStyle(ChatFormatting.WHITE)).append(
                Component.literal(placement.getName()).withStyle(ChatFormatting.YELLOW));
        AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
    }
}
