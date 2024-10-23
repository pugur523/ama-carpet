package org.amateras_smp.amacarpet.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicket;

import java.nio.file.Path;
import java.util.HashSet;

public class ChunkTicketUtil {
    public static HashSet<ChunkTicket<?>> portalTickets = new HashSet<>();

    private final Path portalTicketPath = Path.of(FabricLoader.getInstance().getGameDir() + "/ama_tickets.json");

    private void savePortalTickets(MinecraftServer server) {
    }

    private void addTicket(ChunkTicket<?> ticket) {
        portalTickets.add(ticket);
    }

    private void removeTicket(ChunkTicket<?> ticket) {
        portalTickets.remove(ticket);
    }
}
