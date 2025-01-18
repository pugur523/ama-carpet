// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import carpet.utils.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static void scheduleAuthentication(String playerName) {
        if (!AmaCarpetSettings.requireAmaCarpetClient) return;
        PlayerAuth auth = new PlayerAuth(playerName);
        if (!waitingPlayers.contains(auth)) {
            waitingPlayers.add(auth);
            scheduler.schedule(() -> amaClientCheckForPlayer(auth), AmaCarpetSettings.requireAmaCarpetClientTimeoutSeconds, TimeUnit.SECONDS);
        }
    }

    private static void amaClientCheckForPlayer(PlayerAuth auth) {
        if (!auth.isAuthorized()) {
            if (!AmaCarpetSettings.requireAmaCarpetClient) return;
            ServerPlayer player = AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().getPlayerByName(auth.getName());
            if (player != null) {
                AmaCarpet.LOGGER.debug("disconnect player due to timeout");
                player.connection.disconnect(Component.literal(Translations.tr("ama.message.on_timeout_dc")).withStyle(ChatFormatting.RED));
            }
        }
        waitingPlayers.remove(auth);
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

    private static class PlayerAuth {
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

    public static void onCatchCheater(ServerPlayer player, String cheatName) {
        player.connection.disconnect(Component.literal(String.format(Translations.tr("ama.message.on_cheat_dc"), cheatName)).withStyle(ChatFormatting.RED));
    }
}
