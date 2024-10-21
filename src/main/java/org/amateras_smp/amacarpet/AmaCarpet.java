package org.amateras_smp.amacarpet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmaCarpet implements ModInitializer {

    public static String MOD_NAME = "AmaCarpet";
    public static String MOD_ID = "ama-carpet";
    public static String MOD_VERSION = "1.0.0";
    public static Logger LOGGER;

    @Override
    public void onInitialize() {
        LOGGER = LoggerFactory.getLogger(MOD_NAME);
        InitHandler.init();
    }
}
