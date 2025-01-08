package org.amateras_smp.amacarpet.mixins.network;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.compat.Dummy;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = AmaCarpet.ModIds.minecraft, versionPredicates = ">=1.20.2-alpha.0"))
@Mixin(Dummy.class)
public abstract class CustomPayloadC2SPacketMixin
{
}
