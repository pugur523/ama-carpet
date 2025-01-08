package org.amateras_smp.amacarpet.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerUtil {
    private static final List<PlayerAuth> authPlayers = new ArrayList<>();
    private static final int TIMEOUT_TICKS = 40;
    private static final String dcMessage = "AmaCarpet Client not found. This server requires client with AmaCarpet installed so retry after installing AmaCarpet Mod from modrinth or github.";

    public static void amaClientCheckCanJoinOnTick() {
        if (!AmaCarpetSettings.requireAmaCarpetClient) return;
        for (PlayerAuth auth : authPlayers) {
            if (auth.authorized) {
                authPlayers.remove(auth);
            } else if (auth.loginTick++ == TIMEOUT_TICKS) {
                ServerPlayerEntity player = AmaCarpetServer.minecraft_server.getPlayerManager().getPlayer(auth.name);
                if (player != null) {
                    player.networkHandler.disconnect(Text.literal(dcMessage).formatted(Formatting.RED));
                    authPlayers.remove(auth);
                }
            }
        }
    }

    public static void addShouldAuthPlayer(String name) {
        PlayerAuth auth = new PlayerAuth(name);
        if (authPlayers.contains(auth)) {
            AmaCarpetServer.LOGGER.warn("duplicate authentication entry for {}", name);
        }
        authPlayers.add(auth);
    }

    public static void markAsVerified(String verifiedName) {
        for (PlayerAuth auth : authPlayers) {
            if (Objects.equals(auth.name, verifiedName)) {
                auth.authorized = true;
                break;
            }
        }
    }

    public static class PlayerAuth {
        String name;
        int loginTick;
        boolean authorized;

        public PlayerAuth(String name) {
            this.name = name;
            loginTick = 0;
            authorized = false;
        }
    }
}
