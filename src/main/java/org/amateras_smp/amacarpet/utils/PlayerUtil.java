package org.amateras_smp.amacarpet.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.amateras_smp.amacarpet.AmaCarpetSettings;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtil {
    public static final Map<ServerPlayerEntity, Integer> waitingPlayers = new HashMap<>();
    private static final String dcMessage = "AmaCarpet Client not found.\nThis server requires client with AmaCarpet installed so retry after installing AmaCarpet Mod from modrinth or github.";

    public static boolean amaClientCheck(ServerPlayerEntity player) {
        System.out.println("debug: ama client check");
        if (!AmaCarpetSettings.requireAmaCarpetClient) return true;
        if (!waitingPlayers.containsKey(player)) return true;
        int waitingTicks = waitingPlayers.getOrDefault(player, 0) + 1;
        waitingPlayers.put(player, waitingTicks);
        if (waitingTicks >= 40) {
            player.networkHandler.disconnect(Text.literal(dcMessage).formatted(Formatting.RED));
            waitingPlayers.remove(player);
            return true;
        } else {
            return false;
        }

    }
}
