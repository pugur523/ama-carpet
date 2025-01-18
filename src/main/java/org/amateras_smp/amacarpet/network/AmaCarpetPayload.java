// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.ResourceLocationUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import io.netty.buffer.Unpooled;

//#if MC >= 12002
//$$ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//#if MC >= 12004
//$$ import org.jetbrains.annotations.NotNull;
//#if MC >= 12005
//$$ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//$$ import net.minecraft.network.codec.ByteBufCodecs;
//$$ import net.minecraft.network.codec.StreamCodec;
//$$ import net.minecraft.network.RegistryFriendlyByteBuf;
//#endif
//#endif
//#endif

public class AmaCarpetPayload
        //#if MC >= 12002
        //$$ implements CustomPacketPayload
        //#endif
{
    public final byte[] content;

    public AmaCarpetPayload(byte[] content) {
        this.content = content;
    }

    //#if MC >= 12002
    //$$ public AmaCarpetPayload(FriendlyByteBuf friendlyByteBuf) {
    //$$     this.content = friendlyByteBuf.readByteArray();
    //$$ }
    //#endif

    public byte[] content() {
        return this.content;
    }

    public static final ResourceLocation identifier = ResourceLocationUtil.of(AmaCarpet.kModId, "amacm");

    //#if MC >= 12005
    //$$ public static final Type<AmaCarpetPayload> TYPE = new Type<>(identifier);
    //$$ private static final StreamCodec<RegistryFriendlyByteBuf, AmaCarpetPayload> CODEC = StreamCodec.composite(
    //$$         ByteBufCodecs.BYTE_ARRAY, AmaCarpetPayload::content, AmaCarpetPayload::new
    //$$ );
    //$$ @Override
    //$$ public @NotNull Type<? extends CustomPacketPayload> type() {
    //$$    return TYPE;
    //$$ }
    //#endif

    public static void registerPayload() {
        //#if MC >= 12005
        //$$ PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        //$$ PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
        //#endif
    }

    public void sendC2S() {
        ClientPacketListener networkHandler = Minecraft.getInstance().getConnection();
        if (AmaCarpet.kIsClient && networkHandler != null) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            AmaCarpet.LOGGER.debug("making c2s packet, identifier : {}, content : {}", identifier, content);
            write(buf);
            //#if MC >= 12002
            //$$ ServerboundCustomPayloadPacket packet = new ServerboundCustomPayloadPacket(this);
            //#else
            ServerboundCustomPayloadPacket packet = new ServerboundCustomPayloadPacket(buf);
            //#endif
            networkHandler.send(packet);
        } else {
            AmaCarpet.LOGGER.debug("this is not client or client connection is null");
        }
    }

    public void sendS2C(ServerPlayer player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        write(buf);
        //#if MC >= 12002
        //$$ ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(this);
        //#else
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(buf);
        //#endif
        player.connection.send(packet);
    }

    //#if 12002 <= MC && MC <= 12004
    //$$ @Override
    //#endif
    public void write(FriendlyByteBuf buf)
    {
        buf.writeResourceLocation(identifier);
        buf.writeByteArray(content);
    }

    //#if 12002 <= MC && MC <= 12004
    //$$ @Override
    //#endif
    public ResourceLocation id()
    {
        return identifier;
    }
}
