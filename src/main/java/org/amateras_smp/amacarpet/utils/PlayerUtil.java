package org.amateras_smp.amacarpet.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerUtil {
    private static final Set<PlayerAuth> waitingPlayers = new HashSet<>();
    private static final int TIMEOUT_MS = 2000;
    private static final String DISCONNECT_MESSAGE = "AmaCarpet Client not found. This server requires the AmaCarpet Mod. Please install it from Modrinth or GitHub and try again.";
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static void scheduleAuthentication(String playerName) {
        PlayerAuth auth = new PlayerAuth(playerName);
        waitingPlayers.add(auth);
        scheduler.schedule(() -> amaClientCheckForPlayer(auth), TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    private static void amaClientCheckForPlayer(PlayerAuth auth) {
        if (!auth.isAuthorized()) {
            if (!AmaCarpetSettings.requireAmaCarpetClient) {
                return;
            }
            ServerPlayerEntity player = AmaCarpetServer.MINECRAFT_SERVER.getPlayerManager().getPlayer(auth.getName());
            if (player != null) {
                AmaCarpet.LOGGER.debug("disconnect player due to timeout");
                player.networkHandler.disconnect(Text.literal(DISCONNECT_MESSAGE).formatted(Formatting.RED));
            }
        }
    }

    public static void addShouldAuthPlayer(String name) {
        if (waitingPlayers.stream().anyMatch(auth -> Objects.equals(auth.getName(), name))) {
            AmaCarpetServer.LOGGER.warn("Duplicate authentication entry for {}", name);
            return;
        }
        scheduleAuthentication(name);
    }

    public static void markAsAuthenticated(String name) {
        boolean found = false;
        for (PlayerAuth auth : waitingPlayers) {
            if (auth.getName().equals(name)) {
                auth.setAuthorized(true);
                found = true;
                break;
            }
        }
        if (!found) {
            AmaCarpetServer.LOGGER.error("player {} not found", name);
        }
    }

    public static class PlayerAuth {
        private final String name;
        private boolean authorized;

        public PlayerAuth(String name) {
            this.name = name;
            this.authorized = false;
        }

        public String getName() {
            return name;
        }

        public boolean isAuthorized() {
            return authorized;
        }

        public void setAuthorized(boolean authorized) {
            this.authorized = authorized;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerAuth that = (PlayerAuth) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
