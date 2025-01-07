package org.amateras_smp.amacarpet;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.RegisterPackets;
import org.amateras_smp.amacarpet.network.packets.HandShake;
import java.util.concurrent.TimeUnit;

import static org.amateras_smp.amacarpet.AmaCarpet.MOD_VERSION;
import static org.amateras_smp.amacarpet.network.RegisterPackets.HANDSHAKE_C2S;
import static org.amateras_smp.amacarpet.network.RegisterPackets.HANDSHAKE_S2C;

public class InitHandler {


    public static void init() {
        AmaCarpetServer.init();
        EnvType envType = FabricLoader.getInstance().getEnvironmentType();
        if (envType == EnvType.SERVER) {
            initServer();
        } else if (envType == EnvType.CLIENT) {
            initClient();
        }
    }

    private static void initServer() {
        RegisterPackets.registerServer();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PacketByteBuf packetByteBuf =  new PacketByteBuf(Unpooled.buffer());
            packetByteBuf.writeString(MOD_VERSION);

            ServerPlayerEntity player = handler.getPlayer();
            HandShake.playerModStatus.put(player, false);
            // sender.sendPacket(HANDSHAKE_S2C, packetByteBuf);
            NbtCompound nbt = new NbtCompound();
            nbt.put("mod_version", NbtString.of(MOD_VERSION));
            PacketHandler.sendS2C(handler, HANDSHAKE_S2C, nbt);

            HandShake.executor.schedule(() -> {
                Boolean hasMod = HandShake.playerModStatus.getOrDefault(player, false);
                if (!hasMod) {
                    if (AmaCarpetSettings.requireAmaCarpetClient) {
                        player.networkHandler.disconnect(Text.literal("AmaCarpet-Client is required in this server.\nTry after install it from modrinth or github.").formatted(Formatting.RED));
                    }
                }
            }, 2, TimeUnit.SECONDS);
        });
    }

    private static void initClient() {
        RegisterPackets.registerClient();
    }
}
