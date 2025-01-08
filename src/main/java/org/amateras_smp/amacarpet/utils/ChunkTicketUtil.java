package org.amateras_smp.amacarpet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpetServer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;


public class ChunkTicketUtil {

    private static final String portalTicketFileName = "ama_carpet/portal_tickets.json";

    private static final String OW = "overworld";
    private static final String NE = "nether";
    private static final String END = "end";

    public static HashSet<Long> owPortalTickets = new HashSet<>();
    public static HashSet<Long> nePortalTickets = new HashSet<>();
    public static HashSet<Long> endPortalTickets = new HashSet<>();

    private static Path portalTicketPath;
    private static final Gson gson = new Gson();

    public static void setPath(MinecraftServer server) {
        try {
            Path saveDirectory = server.getSavePath(WorldSavePath.ROOT);
            portalTicketPath = saveDirectory.resolve(portalTicketFileName);
            Files.createDirectories(portalTicketPath.getParent());
        } catch (Exception e) {
            AmaCarpetServer.LOGGER.error("Failed to set portal tickets path", e);
        }
    }

    private static void clearJson() {
        try (FileWriter writer = new FileWriter(String.valueOf(portalTicketPath))) {
            writer.write("");
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("failed clearing json : {}", e.getMessage());
        }
    }

    public static void load(MinecraftServer server) throws IOException {
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

            ServerWorld ow = server.getWorld(World.OVERWORLD);
            ServerWorld ne = server.getWorld(World.NETHER);
            ServerWorld end = server.getWorld(World.END);

            if (ow != null) {
                for (long pos : owPortalTickets2) {
                    ChunkPos chunkPos = new ChunkPos(pos);
                    AmaCarpetServer.LOGGER.info("portal ticket at the chunk({}, {}) of {} has been reloaded", chunkPos.x, chunkPos.z, ow.getRegistryKey().getValue());
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    ow.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
                }
            } else AmaCarpetServer.LOGGER.error(OW + " is null");

            if (ne != null) {
                for (long pos : nePortalTickets2) {
                    ChunkPos chunkPos = new ChunkPos(pos);
                    AmaCarpetServer.LOGGER.info("portal ticket at the chunk({}, {}) of {} has been reloaded", chunkPos.x, chunkPos.z, ne.getRegistryKey().getValue());
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    ne.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 3, blockPos);
                }
            } else AmaCarpetServer.LOGGER.error(NE + " is null");

            if (end != null) {
                for (long pos : endPortalTickets2) {
                    ChunkPos chunkPos = new ChunkPos(pos);
                    AmaCarpetServer.LOGGER.info("portal ticket at the chunk({}, {}) of {} has been reloaded", chunkPos.x, chunkPos.z, end.getRegistryKey().getValue());
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    end.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 3, blockPos);
                }
            } else AmaCarpetServer.LOGGER.error(END + " is null");

            AmaCarpetServer.LOGGER.info("All portal tickets have been reloaded successfully");
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("Failed to reload portal tickets", e);
        }
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

    public static void addTicket(long chunkPosLong, RegistryKey<World> dimension) {
        if (dimension == World.OVERWORLD) {
            owPortalTickets.add(chunkPosLong);
        } else if (dimension == World.NETHER) {
            nePortalTickets.add(chunkPosLong);
        } else if (dimension == World.END) {
            endPortalTickets.add(chunkPosLong);
        } else {
            AmaCarpetServer.LOGGER.error("unknown dimension : {}", dimension.getValue());
        }
    }
}
