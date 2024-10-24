package org.amateras_smp.amacarpet.mixins.ticket;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.*;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.crash.CrashException;
import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.utils.ChunkTicketUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class MixinThreadedAnvilChunkStorage {

    @Shadow
    @Final
    ServerWorld world;

    @Shadow public abstract ChunkTicketManager getTicketManager();

    @Inject(method = "crash", at = @At("HEAD"))
    private void onServerCrash(IllegalStateException exception, String details, CallbackInfoReturnable<CrashException> cir) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        ChunkTicketManager ticketManager = this.getTicketManager();
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
