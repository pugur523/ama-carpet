package org.amateras_smp.amacarpet.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.IdentifierUtil;
import org.amateras_smp.amacarpet.utils.compat.CustomPayload;

import java.util.Objects;

//#if MC >= 12005
//$$ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//$$ import net.minecraft.network.codec.PacketCodecs;
//$$ import net.minecraft.network.codec.PacketCodec;
//$$ import net.minecraft.network.packet.CustomPayload;
//#endif

public class AmaCarpetPacketPayload implements CustomPayload
{
    public final byte[] content;
    public AmaCarpetPacketPayload(byte[] content) {
        this.content = content;
    }
    public byte[] content() {
        return this.content;
    }

    public static final Identifier identifier = IdentifierUtil.of(AmaCarpet.MOD_ID, "amacm");

    //#if MC >= 12005
    //$$ public static final CustomPayload.Id<AmaCarpetPacketPayload> TYPE = new CustomPayload.Id<>(identifier);
    //$$ private static final PacketCodec<PacketByteBuf, AmaCarpetPacketPayload> CODEC = PacketCodec.tuple(
    //$$         PacketCodecs.BYTE_ARRAY, AmaCarpetPacketPayload::content, AmaCarpetPacketPayload::new
    //$$ );
    //#endif

    public static void register() {
        //#if MC >= 12005
        //$$ PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        //$$ PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
        //#endif
    }

    public void sendC2S() {
        //#if MC >= 12004
        //$$ ClientPlayNetworking.send((CustomPayload)this);
        //#else
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeByteArray(content);
        ClientPlayNetworking.send(Objects.requireNonNull(identifier), buf);
        //#endif
    }

    public void sendS2C(ServerPlayerEntity player) {
        //#if MC >= 12004
        //$$ ServerPlayNetworking.send(player, (CustomPayload)this);
        //#else
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeByteArray(content);
        ServerPlayNetworking.send(player, Objects.requireNonNull(identifier), buf);
        //#endif
    }

    //#if MC < 12005
    @Override
    //#endif
    public void write(PacketByteBuf buf) {
        // noop
    }

    //#if MC < 12005
    @Override
    //#endif
    public Identifier id() {
        return identifier;
    }

    //#if MC >= 12005
    //$$ @Override
    //$$ public Id<? extends CustomPayload> getId()
    //$$ {
    //$$ 	return TYPE;
    //$$ }
    //#endif
}
