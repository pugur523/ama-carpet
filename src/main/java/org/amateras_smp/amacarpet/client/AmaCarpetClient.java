package org.amateras_smp.amacarpet.client;

import net.fabricmc.api.ClientModInitializer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmaCarpetClient implements ClientModInitializer {

    public static Logger LOGGER;

    public static boolean isConnected = false;
    @Override
    public void onInitializeClient() {
        LOGGER = LogManager.getLogger(AmaCarpet.MOD_NAME);

        ClientInitHandler.init();

        LOGGER.info("{} client has initialized! (v{})", AmaCarpet.MOD_NAME, AmaCarpet.getVersion());
    }
}
