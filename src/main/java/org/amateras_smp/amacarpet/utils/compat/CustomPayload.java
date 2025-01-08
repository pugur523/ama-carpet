package org.amateras_smp.amacarpet.utils.compat;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

/**
 * Backported from mc1.20.2, for easier networking coding
 */
public interface CustomPayload
{
    void write(PacketByteBuf buf);

    Identifier id();
}