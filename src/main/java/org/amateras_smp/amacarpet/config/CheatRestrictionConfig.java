// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.config;

import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.amateras_smp.amacarpet.utils.FileUtil;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class CheatRestrictionConfig {

    private static final Properties properties = new Properties();
    private static final String configFileName = "cheat_restriction.properties";
    private static final String configComment = "Cheat Restriction Config File - `true` means the feature is allowed";
    private static final CheatRestrictionConfig instance = new CheatRestrictionConfig();

    private CheatRestrictionConfig() {
        setDefaultProperties();
        Path configFilePath = FileUtil.getServerConfigDir().resolve(configFileName);
        try {
            if (Files.notExists(configFilePath)) {
                createDefaultConfig(configFilePath);
            }
            loadProperties(configFilePath);
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Unable to load configuration file: " + e.getMessage(), e);
        }
    }

    public static CheatRestrictionConfig getInstance() {
        return instance;
    }

    private void loadProperties(Path configFilePath) throws IOException {
        try (InputStream input = Files.newInputStream(configFilePath)) {
            properties.load(input);
        }
    }

    private void createDefaultConfig(Path configFilePath) {
        saveConfig(configFilePath);
    }

    private void setDefaultProperties() {
        for (String feature : ClientModUtil.tweakerooFeaturesWatchList) {
            properties.setProperty(feature, "true");
        }
        for (String yeet : ClientModUtil.tweakerooYeetsWatchList) {
            properties.setProperty(yeet, "true");
        }
        for (String feature : ClientModUtil.tweakermoreWatchList) {
            properties.setProperty(feature, "true");
        }
        for (String feature : ClientModUtil.litematicaWatchList) {
            properties.setProperty(feature, "true");
        }
    }

    private void saveConfig(Path configFilePath) {
        try (OutputStream output = Files.newOutputStream(configFilePath)) {
            properties.store(output, configComment);
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Couldn't save cheat-restriction config: ", e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            AmaCarpet.LOGGER.warn("Property not found for key: " + key);
        }
        return value;
    }

    public void set(String key, String value) {
        if (properties.containsKey(key)) {
            properties.setProperty(key, value);
            saveConfig(FileUtil.getServerConfigDir().resolve(configFileName));
        } else {
            AmaCarpet.LOGGER.error("Unknown key tried to be set: " + key);
        }
    }
}
