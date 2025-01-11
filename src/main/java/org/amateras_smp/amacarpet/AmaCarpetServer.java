// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.SettingsManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.amateras_smp.amacarpet.commands.CommandTreeContext;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AmaCarpetServer implements CarpetExtension {

    private static final AmaCarpetServer INSTANCE = new AmaCarpetServer();
    public static final String COMPACT_NAME = AmaCarpet.MOD_ID.replaceAll("-","");  // should be `amacarpet`
    public static final Logger LOGGER = AmaCarpet.LOGGER;
    public static MinecraftServer MINECRAFT_SERVER;

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
        MINECRAFT_SERVER = server;
    }

    @Override
    public void onTick(MinecraftServer server) {
    }

    @Override
    public void registerCommands(
            CommandDispatcher<CommandSourceStack> dispatcher
            //#if MC >= 11900
            , CommandBuildContext commandBuildContext
            //#endif
    ) {
        CommandTreeContext.Register context = CommandTreeContext.of(
                dispatcher
                //#if MC >= 11900
                , commandBuildContext
                //#endif
        );
        /*
        Lists.newArrayList(
                // HopperLockCommand.getInstance();
        ).forEach(command ->
                command.registerCommand(context)
        );

         */
    }

    //#if MC >= 11900
    @Override
    public SettingsManager extensionSettingsManager() {
        return null;
    }
    //#endif

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        InputStream langFile = AmaCarpet.class.getClassLoader().getResourceAsStream("assets/ama-carpet/lang/%s.json".formatted(lang));
        if (langFile == null) {
            return Map.of();
        }
        String jsonData;
        try {
            jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Map.of();
        }
        return new Gson().fromJson(jsonData, new TypeToken<Map<String, String>>() {}.getType());
    }
}
