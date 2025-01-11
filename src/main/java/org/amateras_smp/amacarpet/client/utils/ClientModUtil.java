// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.utils;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBoolean;
import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.AmaCarpet;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientModUtil {
    public static final ImmutableList<String> tweakerooFeaturesWatchList = ImmutableList.of(
            // contains tweakfork
            "accurate_block_placement",
            "block_reach_override",
            "container_scan",
            "fake_sneaking",
            "fast_block_placement",
            "flexible_block_placement",
            "free_camera",
            "gamma_override",
            "no_sneak_slowdown",
            "scaffold_place"
    );

    public static final ImmutableList<String> tweakerooYeetsWatchList = ImmutableList.of(
            // contains masa addition
            "honey_block_slowdown",
            "honey_block_jumping",
            "slime_block_bouncing",
            "slime_block_slowdown"
    );

    public static final ImmutableList<String> tweakermoreWatchList = ImmutableList.of(
            "auto_pick_schematic_block",
            "disable_darkness_effect",
            "fake_night_vision",
            "schematic_block_placement_restriction",
            "schematic_pro_place"
    );

    public static final ImmutableList<String> litematicaWatchList = ImmutableList.of(
            "easy_place_mode",
            "placement_restriction"
    );

    private static boolean checkMalilibConfigBoolean(String className, String feature) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(feature);
            field.setAccessible(true);
            Object value = field.get(null);

            if (value instanceof IConfigBoolean configBoolean) {
                return configBoolean.getBooleanValue();
            }
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            AmaCarpet.LOGGER.error("can't get tweakeroo feature state for class {} : ", className,  e);
        }
        return false;
    }

    private static void addFeatures(Map<String, Boolean> map, String modId, List<String> features, String className, String featurePrefix) {
        boolean isModLoaded = FabricLoader.getInstance().isModLoaded(modId);
        for (String feature : features) {
            boolean value = false;
            if (isModLoaded) {
                if (featurePrefix == null) featurePrefix = "";
                String featureFormatted = featurePrefix + feature.toUpperCase();
                value = checkMalilibConfigBoolean(className, featureFormatted);
            }
            map.put(feature, value);
        }
    }

    public static HashMap<String, Boolean> createConfigDataMap() {
        HashMap<String, Boolean> map = new HashMap<>();

        addFeatures(map, AmaCarpet.ModIds.tweakeroo, tweakerooFeaturesWatchList, "fi.dy.masa.tweakeroo.config.FeatureToggle", "TWEAK_");
        addFeatures(map, AmaCarpet.ModIds.tweakeroo, tweakerooYeetsWatchList, "fi.dy.masa.tweakeroo.config.Configs$Disable", "DISABLE_");
        addFeatures(map, AmaCarpet.ModIds.tweakermore, tweakermoreWatchList, "me.fallenbreath.tweakermore.config.TweakerMoreConfigs", "");
        addFeatures(map, AmaCarpet.ModIds.litematica, litematicaWatchList, "fi.dy.masa.litematica.config.Configs$Generic", "");

        return map;
    }
}
