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
import org.amateras_smp.amacarpet.AmaCarpet;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;


public class ChunkTicketUtil {

    private static final String portalTicketFileName = "ama_tickets.json";

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

            AmaCarpet.LOGGER.info("Portal tickets path set to {}", portalTicketPath.toString());
        } catch (Exception e) {
            AmaCarpet.LOGGER.error("Failed to set portal tickets path", e);
        }
    }

    private static void clearJson() {
        try (FileWriter writer = new FileWriter(String.valueOf(portalTicketPath))) {
            writer.write("");
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("failed clearing json : {}", e.getMessage());
        }
    }

    public static void load(MinecraftServer server) {
        if (portalTicketPath == null) {
            AmaCarpet.LOGGER.warn("Portal ticket path is not set.");
            return;
        }

        if (!Files.exists(portalTicketPath)) {
            AmaCarpet.LOGGER.warn("No portal tickets file found at {}", portalTicketPath.toString());
            return;
        }

        try {
            String jsonContent = Files.readString(portalTicketPath);
            JsonObject ticketData = gson.fromJson(jsonContent, JsonObject.class);

            HashSet<Long> owPortalTickets2 = new HashSet<>();
            HashSet<Long> nePortalTickets2 = new HashSet<>();
            HashSet<Long> endPortalTickets2 = new HashSet<>();

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
                    AmaCarpet.LOGGER.info("{}'s {}, {} portal ticket has reloaded", ow.getRegistryKey().getValue(), chunkPos.x, chunkPos.z);
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    ow.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
                }
            } else AmaCarpet.LOGGER.error(OW + " is null");

            if (ne != null) {
                for (long pos : nePortalTickets2) {
                    ChunkPos chunkPos = new ChunkPos(pos);
                    AmaCarpet.LOGGER.info("{}'s {}, {} portal ticket has reloaded", ne.getRegistryKey().getValue(), chunkPos.x, chunkPos.z);
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    ne.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 3, blockPos);
                }
            } else AmaCarpet.LOGGER.error(NE + " is null");

            if (end != null) {
                for (long pos : endPortalTickets2) {
                    ChunkPos chunkPos = new ChunkPos(pos);
                    AmaCarpet.LOGGER.info("{}'s {}, {} portal ticket has reloaded", end.getRegistryKey().getValue(), chunkPos.x, chunkPos.z);
                    BlockPos blockPos = chunkPos.getBlockPos(0, 0, 0);
                    end.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 3, blockPos);
                }
            } else AmaCarpet.LOGGER.error(END + " is null");

            AmaCarpet.LOGGER.info("Portal tickets loaded successfully from {}", portalTicketPath.toString());
            clearJson();
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Failed to load portal tickets", e);
        }
    }

    public static void save() {
        if (portalTicketPath == null) {
            AmaCarpet.LOGGER.error("portal ticket path is null");
        }
        JsonObject ticketData = getJsonObject();

        try {
            Files.writeString(portalTicketPath, gson.toJson(ticketData), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            AmaCarpet.LOGGER.info("Portal tickets saved to {}", portalTicketPath.toString());
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Failed to save portal tickets", e);
        }
    }

    @NotNull
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
            AmaCarpet.LOGGER.info("unknown dimension : {}", dimension.getValue());
        }
    }
}
