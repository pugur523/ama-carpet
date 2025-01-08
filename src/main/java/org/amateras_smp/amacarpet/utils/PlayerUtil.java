package org.amateras_smp.amacarpet.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Objects;

public class PlayerUtil {
    private static final CopyOnWriteArrayList<PlayerAuth> AUTH_PLAYERS = new CopyOnWriteArrayList<>();
    private static final int TIMEOUT_TICKS = 40;
    private static final String DISCONNECT_MESSAGE = "AmaCarpet Client not found. This server requires the AmaCarpet Mod. Please install it from Modrinth or GitHub and try again.";

    public static void amaClientCheckCanJoinOnTick() {
        if (!AmaCarpetSettings.requireAmaCarpetClient) return;

        Iterator<PlayerAuth> iterator = AUTH_PLAYERS.iterator();
        while (iterator.hasNext()) {
            PlayerAuth auth = iterator.next();
            if (auth.isAuthorized()) {
                iterator.remove();
            } else if (auth.incrementLoginTick() >= TIMEOUT_TICKS) {
                ServerPlayerEntity player = AmaCarpetServer.minecraft_server.getPlayerManager().getPlayer(auth.getName());
                if (player != null) {
                    player.networkHandler.disconnect(Text.literal(DISCONNECT_MESSAGE).formatted(Formatting.RED));
                }
                iterator.remove();
            }
        }
    }

    public static void addShouldAuthPlayer(String name) {
        synchronized (AUTH_PLAYERS) {
            if (AUTH_PLAYERS.stream().anyMatch(auth -> Objects.equals(auth.getName(), name))) {
                AmaCarpetServer.LOGGER.warn("Duplicate authentication entry for {}", name);
                return;
            }
            AUTH_PLAYERS.add(new PlayerAuth(name));
        }
    }

    public static void markAsVerified(String verifiedName) {
        for (PlayerAuth auth : AUTH_PLAYERS) {
            if (Objects.equals(auth.getName(), verifiedName)) {
                auth.setAuthorized(true);
                break;
            }
        }
    }

    public static class PlayerAuth {
        private final String name;
        private int loginTick;
        private boolean authorized;

        public PlayerAuth(String name) {
            this.name = name;
            this.loginTick = 0;
            this.authorized = false;
        }

        public String getName() {
            return name;
        }

        public int incrementLoginTick() {
            return ++loginTick;
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
