package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.*;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkManager.class)
public class MixinServerChunkManager {
    @Shadow
    @Final
    public ThreadedAnvilChunkStorage threadedAnvilChunkStorage;

    @Shadow
    @Final
    ServerWorld world;

    @Inject(method = "removePersistentTickets", at = @At("HEAD"))
    private void onReleaseTickets(CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketManager ticketManager = this.threadedAnvilChunkStorage.getTicketManager();
        ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>>> objectIterator = ticketManager.ticketsByPosition.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>> entry = objectIterator.next();

            long chunkLong = entry.getLongKey();
            for (ChunkTicket<?> chunkTicket : entry.getValue()) {
                if (chunkTicket.getType() == ChunkTicketType.PORTAL) {
                    RegistryKey<World> dimension = world.getRegistryKey();

                    ChunkTicketUtil.addTicket(chunkLong, dimension);
                }
            }
        }
    }
}
