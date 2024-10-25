package org.amateras_smp.amacarpet.utils;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.client.feature.ServerContainerCache;
import org.amateras_smp.amacarpet.network.packet.S2C.ServerContainerS2CPacket;

public class ServerContainerUtil {
    public static void onQuery(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (!AmaCarpetSettings.serverContainerSync) return;
        BlockPos blockPos = buf.readBlockPos();
        //#if MC >= 12000
        ServerWorld world = player.getServerWorld();
        //#else
        //$$ ServerWorld world = player.getWorld();
        //#endif

        server.execute(()->{
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if( blockEntity == null ) return;

            NbtCompound nbt = blockEntity.createNbt();

            // 2 ^ 19 = 524288
            // idk what is this hell
            //#if MC >= 12000
            if (nbt.getSizeInBytes() > 524288 ) return;
            //#endif

            PacketByteBuf responseBuf =  new PacketByteBuf(Unpooled.buffer());

            responseBuf.writeNbt(nbt);
            responseBuf.writeBlockPos(blockPos);
            responseSender.sendPacket(ServerContainerS2CPacket.SERVER_CONTAINER_PACKET, responseBuf);
        });
    }

    public static void onResponse(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        NbtCompound containerData = buf.readNbt();
        ServerContainerCache.getInstance().put(blockPos, containerData);
    }
}
