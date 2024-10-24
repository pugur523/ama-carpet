package org.amateras_smp.amacarpet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AmaCarpet implements ModInitializer {

    public static String MOD_NAME = "AmaCarpet";
    public static String MOD_ID = "ama-carpet";
    private static String MOD_VERSION;
    public static Logger LOGGER;

    @Override
    public void onInitialize() {
        MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
        LOGGER = LogManager.getLogger(MOD_NAME);
        InitHandler.init();
        LOGGER.info("{} has initialized! (v{})", MOD_NAME, getVersion());
    }

    public static String getVersion() {
        return MOD_VERSION;
    }
}
