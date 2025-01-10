package org.amateras_smp.amacarpet.network;

import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.ResourceLocationUtil;
//#if MC < 12005
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
//#else
//$$ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//$$ import net.minecraft.network.codec.ByteBufCodecs;
//$$ import net.minecraft.network.codec.StreamCodec;
//$$ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//$$ import net.minecraft.network.RegistryFriendlyByteBuf;
//$$ import org.jetbrains.annotations.NotNull;
//#endif

public class AmaCarpetPayload
        //#if MC >= 12005
        //$$ implements CustomPayload
        //#endif
{
    public final byte[] content;

    public AmaCarpetPayload(byte[] content) {
        this.content = content;
    }

    public byte[] content() {
        return this.content;
    }

    public static final ResourceLocation identifier = ResourceLocationUtil.of(AmaCarpet.MOD_ID, "amacm");

    //#if MC >= 12005
    //$$ public static final Type<AmaCarpetPayload> TYPE = new Id<>(identifier);
    //$$ private static final StreamCodec<RegistryFriendlyByteBuf, AmaCarpetPayload> CODEC = StreamCodec.composite(
    //$$         ByteBufCodecs.BYTE_ARRAY, AmaCarpetPayload::content, AmaCarpetPayload::new
    //$$ );
    //$$ @Override
    //$$ public @NotNull Type<? extends CustomPacketPayload> type() {
    //$$    return TYPE;
    //$$ }
    //#endif

    public static void register() {
        //#if MC >= 12005
        //$$ PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        //$$ PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
        //#endif

        if (AmaCarpet.IS_CLIENT) {
            ClientPlayNetworking.registerGlobalReceiver(
                    //#if MC >= 12005
                    //$$ TYPE, AmaCarpetPayload::onPacketClient
                    //#else
                    identifier, (client, handler, buf, responseSender) -> {
                        AmaCarpetPayload packetPayload = new AmaCarpetPayload(buf.readByteArray());
                        packetPayload.onPacketClient(client, handler, responseSender);
                    }
                    //#endif
            );
        } else {
            ServerPlayNetworking.registerGlobalReceiver(
                    //#if MC >= 12005
                    //$$ TYPE, AmaCarpetPayload::onPacketClient
                    //#else
                    identifier, (server, player, handler, buf, responseSender) -> {
                        AmaCarpetPayload packetPayload = new AmaCarpetPayload(buf.readByteArray());
                        packetPayload.onPacketServer(server, player, handler, responseSender);
                    }
                    //#endif
            );
        }
    }

    public void sendC2S() {
        //#if MC >= 12005
        //$$ ClientPlayNetworking.send(this);
        //#else
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByteArray(content);
        ClientPlayNetworking.send(identifier, buf);
        //#endif
    }

    public void sendS2C(ServerPlayer player) {
        //#if MC >= 12005
        //$$ ServerPlayNetworking.send(player, this);
        //#else
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByteArray(content);
        ServerPlayNetworking.send(player, identifier, buf);
        //#endif
    }

    public void onPacketServer(
        //#if MC >= 12005
        //$$ ServerPlayNetworking.Context context
        //#else
        MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender sender
        //#endif
    ) {
        AmaCarpet.LOGGER.debug("onPacketServer");
        PacketHandler.handleC2S(
                this.content,
                //#if MC >= 12005
                //$$ (ServerPlayer) context.player()
                //#else
                player
                //#endif
        );
    }

    public void onPacketClient(
            //#if MC >= 12005
            //$$ ClientPlayNetworking.Context context
            //#else
            Minecraft client, ClientPacketListener listener, PacketSender sender
            //#endif
    ) {
        AmaCarpet.LOGGER.debug("onPacketClient");
        PacketHandler.handleS2C(this.content);
    }
}
