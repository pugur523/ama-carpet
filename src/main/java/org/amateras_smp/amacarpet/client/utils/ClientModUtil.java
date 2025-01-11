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

    public static boolean checkMalilibConfigBoolean(String className, String feature) {
        // assert that tweakeroo (or tweakfork) is already loaded.
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

    public static HashMap<String, Boolean> createConfigDataMap() {
        FabricLoader loader = FabricLoader.getInstance();
        HashMap<String, Boolean> map = new HashMap<>();

        if (loader.isModLoaded(AmaCarpet.ModIds.tweakeroo)) {
            for (String feature : tweakerooFeaturesWatchList) {
                feature = feature.toUpperCase();
                map.put(feature, checkMalilibConfigBoolean("fi.dy.masa.tweakeroo.config.FeatureToggle", feature));
            }
            for (String feature : tweakerooYeetsWatchList) {
                feature = "DISABLE_" + feature.toUpperCase();
                map.put(feature, checkMalilibConfigBoolean("fi.dy.masa.tweakeroo.config.Configs.Disable", feature));
            }
        } else {
            for (String feature : tweakerooFeaturesWatchList) {
                map.put(feature, false);
            }
            for (String feature : tweakerooYeetsWatchList) {
                map.put(feature, false);
            }
        }

        if (loader.isModLoaded(AmaCarpet.ModIds.tweakermore)) {
            for (String feature : tweakermoreWatchList) {
                feature = feature.toUpperCase();
                map.put(feature, checkMalilibConfigBoolean("me.fallenbreath.tweakermore.config.TweakerMoreConfigs", feature));
            }
        } else {
            for (String feature : tweakermoreWatchList) {
                map.put(feature, false);
            }
        }

        if (loader.isModLoaded(AmaCarpet.ModIds.litematica)) {
            for (String feature : litematicaWatchList) {
                feature = feature.toUpperCase();
                map.put(feature, checkMalilibConfigBoolean("fi.dy.masa.litematica.config.Configs.Generic", feature));
            }
        } else {
            for (String feature : litematicaWatchList) {
                map.put(feature, false);
            }
        }

        return map;
    }
}
