// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.storage.LevelResource;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    private static final String configDirName = "ama_carpet";

    public static Path getWorldConfigDir() {
        Path path = AmaCarpetServer.MINECRAFT_SERVER.getWorldPath(LevelResource.ROOT).resolve(configDirName);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("couldn't get world config directory : ", e);
        }
        return path;
    }

    public static Path getServerConfigDir() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(configDirName);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            AmaCarpetServer.LOGGER.error("couldn't get config directory : ", e);
        }
        return path;
    }
}
