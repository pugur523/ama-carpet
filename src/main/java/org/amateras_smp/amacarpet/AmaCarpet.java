// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class AmaCarpet implements ModInitializer {

    public static final String MOD_NAME = "AmaCarpet";
    public static final String MOD_ID = "ama-carpet";
    public static Logger LOGGER;
    public static boolean kIsClient;
    private static String kModVersion;

    @Override
    public void onInitialize() {
        LOGGER = LogManager.getLogger(MOD_NAME);
        Configurator.setLevel(LOGGER, Level.DEBUG);

        FabricLoader fabricLoader = FabricLoader.getInstance();
        kModVersion = fabricLoader.getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
        kIsClient = fabricLoader.getEnvironmentType() == EnvType.CLIENT;
        InitHandler.init();
        LOGGER.info("{}({}) has initialized!", MOD_NAME, getVersion());
    }

    public static String getVersion() {
        return kModVersion;
    }

    public static void setDebug(boolean debug) {
        if (debug) {
            Configurator.setLevel(LOGGER, Level.DEBUG);
            LOGGER.info("[AmaCarpet] Enabled debug mode.");
        } else {
            Configurator.setLevel(LOGGER, Level.ERROR);
            LOGGER.info("[AmaCarpet] Disabled debug mode.");
        }
    }

    public static class ModIds {
        public static final String minecraft = "minecraft";
        public static final String amatweaks = "ama-tweaks";
        public static final String fabric_loader = "fabricloader";
        public static final String fabric_api = "fabric";
        public static final String kyoyu = "kyoyu";
        public static final String malilib = "malilib";
        public static final String tweakeroo = "tweakeroo";
        public static final String tweakermore = "tweakermore";
        public static final String masaadditions = "masaadditions";
        public static final String itemscroller = "itemscroller";
        public static final String litematica = "litematica";
        public static final String syncmatica = "syncmatica";
        public static final String minihud = "minihud";
        public static final String cheat_utils = "cheatutils";
        public static final String compact_chat = "compactchat";
        public static final String more_chat_history = "morechathistory";
        public static final String parachute = "parachute";
        public static final String raise_chat_limit = "raise-chat-limit";
        public static final String wheres_my_chat_history = "wmch";
        public static final String carpet_tis_addition = "carpet-tis-addition";
        public static final String caxton = "caxton";
        public static final String custom_skin_loader = "customskinloader";
        public static final String easier_crafting = "easiercrafting";
        public static final String extra_player_renderer = "explayerenderer";
        public static final String fabric_carpet = "carpet";
        public static final String iris = "iris";
        public static final String locked_window_size = "locked_window_size";
        public static final String optifine = "optifabric";
        public static final String pistorder = "pistorder";
        public static final String replay_mod = "replaymod";
        public static final String sodium = "sodium";
        public static final String xaero_betterpvp = "xaerobetterpvp";
        public static final String xaero_minimap = "xaerominimap";
        public static final String xaero_worldmap = "xaeroworldmap";
    }
}
