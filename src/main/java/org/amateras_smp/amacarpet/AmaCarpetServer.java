package org.amateras_smp.amacarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

public class AmaCarpetServer implements CarpetExtension {

    private static final AmaCarpetServer INSTANCE = new AmaCarpetServer();
    public static final String compactName = AmaCarpet.MOD_ID.replaceAll("-","");  // amacarpet
    public static final Logger LOGGER = AmaCarpet.LOGGER;
    public static MinecraftServer minecraft_server;

    @Override
    public String version()
    {
        return AmaCarpet.MOD_ID;
    }

    public static AmaCarpetServer getInstance()
    {
        return INSTANCE;
    }

    public static void init()
    {
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onGameStarted()
    {
        CarpetServer.settingsManager.parseSettingsClass(AmaCarpetSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server)
    {
        minecraft_server = server;
    }
}
