package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {
    @Inject(method = "Lnet/minecraft/server/world/ChunkTicketManager;addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    private void onAddTicket(long position, ChunkTicket<?> ticket, CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        if (ticket.getType() == ChunkTicketType.PORTAL) {
            AmaCarpet.LOGGER.info("debug : chunk ticket generated");
        }
    }

    @Inject(method = "Lnet/minecraft/server/world/ChunkTicketManager;removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    private void onRemoveTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        if (ticket.getType() == ChunkTicketType.PORTAL) {
            AmaCarpet.LOGGER.info("debug : chunk ticket removed");
        }
    }

}
