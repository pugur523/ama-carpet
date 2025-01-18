// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.SortedArraySet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

public class ChunkTicketUtil {

    private static final String portalTicketFileName = "portal_tickets.json";

    private static final String OW = "overworld";
    private static final String NE = "nether";
    private static final String END = "end";

    public static HashSet<Long> owPortalTickets = new HashSet<>();
    public static HashSet<Long> nePortalTickets = new HashSet<>();
    public static HashSet<Long> endPortalTickets = new HashSet<>();

    private static Path portalTicketPath;
    private static final Gson gson = new Gson();

    public static void setPath() {
        portalTicketPath = FileUtil.getWorldConfigDir().resolve(portalTicketFileName);
    }

    private static void clearJson() {
        try (FileWriter writer = new FileWriter(String.valueOf(portalTicketPath))) {
            writer.write("");
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("failed clearing json : {}", e.getMessage());
        }
    }

    public static void load() throws IOException {
        if (portalTicketPath == null) {
            AmaCarpetServer.LOGGER.error("Portal ticket path is not set.");
            return;
        }

        if (!Files.exists(portalTicketPath)) {
            Files.createFile(portalTicketPath);
            return;
        }

        try {
            String jsonContent = Files.readString(portalTicketPath);
            JsonObject ticketData = gson.fromJson(jsonContent, JsonObject.class);

            HashSet<Long> owPortalTickets2 = new HashSet<>();
            HashSet<Long> nePortalTickets2 = new HashSet<>();
            HashSet<Long> endPortalTickets2 = new HashSet<>();

            if (ticketData == null) return;
            if (ticketData.isJsonNull()) return;
            JsonArray owArray = null;
            JsonArray neArray = null;
            JsonArray endArray = null;
            if (!ticketData.get(OW).isJsonNull()) {
                owArray = ticketData.getAsJsonArray(OW);
            }
            if (!ticketData.get(NE).isJsonNull()) {
                neArray = ticketData.getAsJsonArray(NE);
            }
            if (!ticketData.get(END).isJsonNull()) {
                endArray = ticketData.getAsJsonArray(END);
            }

            if (owArray != null && !owArray.isEmpty()) {
                for (JsonElement element : owArray) {
                    long pos = element.getAsLong();
                    owPortalTickets2.add(pos);
                }
            }
            if (neArray != null && !neArray.isEmpty()) {
                for (JsonElement element : neArray) {
                    long pos = element.getAsLong();
                    nePortalTickets2.add(pos);
                }
            }
            if (endArray != null && !endArray.isEmpty()) {
                for (JsonElement element : endArray) {
                    long pos = element.getAsLong();
                    endPortalTickets2.add(pos);
                }
            }

            ServerLevel ow = AmaCarpetServer.MINECRAFT_SERVER.getLevel(Level.OVERWORLD);
            loadDimension(ow, owPortalTickets2, Level.OVERWORLD);
            ServerLevel ne = AmaCarpetServer.MINECRAFT_SERVER.getLevel(Level.NETHER);
            loadDimension(ne, nePortalTickets2, Level.NETHER);
            ServerLevel end = AmaCarpetServer.MINECRAFT_SERVER.getLevel(Level.END);
            loadDimension(end, endPortalTickets2, Level.END);

            AmaCarpetServer.LOGGER.info("All portal tickets have been reloaded successfully");
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("Failed to reload portal tickets", e);
        }
    }

    private static void loadDimension(ServerLevel level, HashSet<Long> portalTickets, ResourceKey<Level> dimension) {
        if (level != null) {
            for (long pos : portalTickets) {
                ChunkPos chunkPos = new ChunkPos(pos);
                AmaCarpetServer.LOGGER.info("portal ticket at the chunk({}, {}) of {} has been reloaded", chunkPos.x, chunkPos.z, level.dimension().location());
                BlockPos blockPos = chunkPos.getBlockAt(0, 0, 0);
                level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
            }
        } else AmaCarpetServer.LOGGER.error("{} is null", dimension.location());
    }

    public static void save() {
        if (portalTicketPath == null) {
            AmaCarpetServer.LOGGER.error("portal ticket path is null");
        }
        JsonObject ticketData = getJsonObject();
        if (ticketData.isJsonNull()) {
            clearJson();
            return;
        }
        try {
            Files.writeString(portalTicketPath, gson.toJson(ticketData), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            AmaCarpetServer.LOGGER.info("Portal tickets saved to {}", portalTicketPath.toString());
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("Failed to save portal tickets", e);
        }
    }

    private static JsonObject getJsonObject() {
        JsonObject ticketData = new JsonObject();

        JsonArray owArray = new JsonArray();
        for (long pos : owPortalTickets) {
            owArray.add(pos);
        }
        ticketData.add(OW, owArray);

        JsonArray neArray = new JsonArray();
        for (long pos : nePortalTickets) {
            neArray.add(pos);
        }
        ticketData.add(NE, neArray);

        JsonArray endArray = new JsonArray();
        for (long pos : endPortalTickets) {
            endArray.add(pos);
        }
        ticketData.add(END, endArray);

        return ticketData;
    }

    private static void addTicket(long chunkPosLong, ResourceKey<Level> dimension) {
        if (dimension == Level.OVERWORLD) {
            owPortalTickets.add(chunkPosLong);
        } else if (dimension == Level.NETHER) {
            nePortalTickets.add(chunkPosLong);
        } else if (dimension == Level.END) {
            endPortalTickets.add(chunkPosLong);
        } else {
            AmaCarpetServer.LOGGER.error("unknown dimension : {}", dimension.location());
        }
    }

    public static void addAllTickets(ServerLevel level) {
        if (!AmaCarpetSettings.reloadPortalTicket) return;
        DistanceManager distanceManager = level.getChunkSource().chunkMap.getDistanceManager();

        // `distanceManager.tickets` can be accessed due to access widener.
        ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>> objectIterator = distanceManager.tickets.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>> entry = objectIterator.next();

            long chunkLong = entry.getLongKey();
            for (Ticket<?> chunkTicket : entry.getValue()) {
                if (chunkTicket.getType() == TicketType.PORTAL) {
                    ResourceKey<Level> dimension = level.dimension();

                    addTicket(chunkLong, dimension);
                }
            }
        }
    }
}
