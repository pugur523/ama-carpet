package org.amateras_smp.amacarpet.mixins.kyoyu;

import com.vulpeus.kyoyu.placement.KyoyuPlacement;
import com.vulpeus.kyoyu.placement.KyoyuRegion;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
@Mixin(KyoyuPlacement.class)
public class MixinKyoyuPlacement {
    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void onAddKyoyuPlacement(UUID uuid, KyoyuRegion region, List<KyoyuRegion> subRegions, String ownerName, String updaterName, File file, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifyLitematicShared) return;
        KyoyuPlacement placement = (KyoyuPlacement)(Object) this;
        Text message = Text.literal(ownerName).formatted(Formatting.GREEN).append(
                Text.literal(" shared a litematic! \nPlacement name : ").formatted(Formatting.WHITE)).append(
                Text.literal(placement.getName()).formatted(Formatting.YELLOW));
        AmaCarpetServer.minecraft_server.getPlayerManager().broadcast(message, false);
    }
}
