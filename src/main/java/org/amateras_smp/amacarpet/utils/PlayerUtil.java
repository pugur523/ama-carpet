package org.amateras_smp.amacarpet.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.mixins.network.AccessorServerLoginNetworkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerUtil {
    public static final List<GameProfile> verifiedPlayers = new ArrayList<>();
    public static final List<ServerLoginNetworkHandler> waitingPlayers = new ArrayList<>();
    private static final String dcMessage = "AmaCarpet Client not found. This server requires client with AmaCarpet installed so retry after installing AmaCarpet Mod from modrinth or github.";
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void amaClientCheckCanJoin(ServerLoginNetworkHandler handler) {
        scheduler.schedule(() -> {
            if (!AmaCarpetSettings.requireAmaCarpetClient) return true;
            if (!waitingPlayers.contains(handler)) return true;
            GameProfile profile = ((AccessorServerLoginNetworkHandler)handler).getProfile();
            if (verifiedPlayers.contains(profile)) {
                waitingPlayers.remove(handler);
                verifiedPlayers.remove(profile);
                return true;
            }
            waitingPlayers.remove(handler);
            handler.disconnect(Text.literal(dcMessage).formatted(Formatting.RED));
            return false;
        }, 1, TimeUnit.SECONDS);
    }
}
