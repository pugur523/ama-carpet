package org.amateras_smp.amacarpet.mixins.network;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Restriction(require = @Condition(value = AmaCarpet.ModIds.minecraft, versionPredicates = "<1.20.2-alpha.0"))
@Mixin(CustomPayloadS2CPacket.class)
public interface CustomPayloadS2CPacketAccessor
{
    //#if MC < 12002
    @Accessor
    Identifier getChannel();
    //#endif
}