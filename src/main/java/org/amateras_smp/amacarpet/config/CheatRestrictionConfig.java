package org.amateras_smp.amacarpet.config;

import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.FileUtil;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class CheatRestrictionConfig {

    private static final Properties properties = new Properties();
    private static final String configFileName = "cheat_restriction.properties";
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
        // tweakeroo(tweakfork)
        properties.setProperty("allow_accurate", "true");
        properties.setProperty("allow_block_reach_override", "true");
        properties.setProperty("allow_container_scan", "true");
        properties.setProperty("allow_fake_sneak", "true");
        properties.setProperty("allow_fast_placement", "true");
        properties.setProperty("allow_flexible", "true");
        properties.setProperty("allow_free_camera", "true");
        properties.setProperty("allow_gamma_override", "true");
        properties.setProperty("allow_no_sneak_slowdown", "true");
        properties.setProperty("allow_scaffold_place", "true");
        properties.setProperty("allow_yeet_honey_bounce", "true");
        properties.setProperty("allow_yeet_honey_slowdown", "true");
        properties.setProperty("allow_yeet_slime_bounce", "true");
        properties.setProperty("allow_yeet_slime_slowdown", "true");

        // tweakermore
        properties.setProperty("allow_auto_pick_schema", "true");
        properties.setProperty("allow_disable_darkness", "true");
        properties.setProperty("allow_fake_night_vision", "true");
        properties.setProperty("allow_schema_placement_restriction", "true");
        properties.setProperty("allow_schema_pro_place", "true");

        // litematica
        properties.setProperty("allow_easy_place", "true");
        properties.setProperty("allow_placement_restriction", "true");
    }

    private void saveConfig(Path configFilePath) {
        try (OutputStream output = Files.newOutputStream(configFilePath)) {
            properties.store(output, "Creating save cheat-restriction config.");
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
