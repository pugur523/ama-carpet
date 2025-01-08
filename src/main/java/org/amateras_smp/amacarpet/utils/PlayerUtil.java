package org.amateras_smp.amacarpet.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerUtil {
    private static final List<PlayerAuth> authPlayers = new ArrayList<>();
    private static final String dcMessage = "AmaCarpet Client not found. This server requires client with AmaCarpet installed so retry after installing AmaCarpet Mod from modrinth or github.";

    public static void amaClientCheckCanJoinOnTick() {
        if (!AmaCarpetSettings.requireAmaCarpetClient) return;
        for (PlayerAuth auth : authPlayers) {
            if (auth.authorized) {
                authPlayers.remove(auth);
            } else if (auth.loginTick++ >= 600) {
                Objects.requireNonNull(AmaCarpetServer.minecraft_server.getPlayerManager().getPlayer(auth.profile.getId())).networkHandler.disconnect(Text.literal(dcMessage).formatted(Formatting.RED));
            }
        }
    }

    public static void addShouldAuthPlayer(GameProfile profile) {
        PlayerAuth auth = new PlayerAuth(profile);
        if (authPlayers.contains(auth)) {
            AmaCarpetServer.LOGGER.warn("duplicate authentication entry for {}", profile.getName());
        }
        authPlayers.add(auth);
    }

    public static void markAsVerified(GameProfile profile) {
        for (PlayerAuth auth : authPlayers) {
            if (Objects.equals(auth.profile.getName(), profile.getName())) {
                auth.authorized = true;
                break;
            }
        }
    }

    public static class PlayerAuth {
        GameProfile profile;
        int loginTick;
        boolean authorized;

        public PlayerAuth(GameProfile profile) {
            this.profile = profile;
            loginTick = 0;
            authorized = false;
        }
    }
}
