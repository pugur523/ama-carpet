package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.math.ChunkPos;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class MixinChunkTicketManager {
    @Shadow
    @Final
    public Long2ObjectOpenHashMap<SortedArraySet<ChunkTicket<?>>> ticketsByPosition;

    @Inject(method = "addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    private void onAddTicket(long position, ChunkTicket<?> ticket, CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        if (ticket.getType() == ChunkTicketType.PORTAL) {
            AmaCarpet.LOGGER.info("debug : chunk ticket generated");
        }
    }

    @Inject(method = "removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    private void onRemoveTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        if (ticket.getType() == ChunkTicketType.PORTAL) {
            AmaCarpet.LOGGER.info("debug : chunk ticket removed");
        }
    }

    @Inject(method = "removePersistentTickets", at = @At("HEAD"))
    private void onServerShutdown(CallbackInfo ci) {
        ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>>> objectIterator = ticketsByPosition.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>> entry = objectIterator.next();

            long chunkLong = entry.getLongKey();
            for (ChunkTicket<?> chunkTicket : entry.getValue()) {
                if (chunkTicket.getType() == ChunkTicketType.PORTAL) {
                    if (chunkTicket.argument instanceof ChunkPos) {
                        AmaCarpet.LOGGER.info("test {}, {}", ((ChunkPos) chunkTicket.argument).x, ((ChunkPos) chunkTicket.argument).z);
                    }
                    AmaCarpet.LOGGER.info("chunkPos : {}, ticket : {}", chunkLong, chunkTicket);
                }
            }
        }

    }
}
