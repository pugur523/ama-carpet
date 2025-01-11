package org.amateras_smp.amacarpet.client.utils;

import com.google.common.collect.ImmutableList;
import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    public static boolean isFeatureEnable(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            if (!Modifier.isStatic(field.getModifiers())) {
                AmaCarpetClient.LOGGER.error("the field must be static");
                return false;
            }
            field.setAccessible(true);
            return field.getBoolean(null);
        } catch (ClassNotFoundException e) {
            return false;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            AmaCarpetClient.LOGGER.error(e);
            return false;
        }
    }

    public static HashMap<String, Boolean> createConfigDataMap() {
        FabricLoader loader = FabricLoader.getInstance();
        HashMap<String, Boolean> map = new HashMap<>();

        if (loader.isModLoaded(AmaCarpet.ModIds.tweakeroo)) {
        } if (loader.isModLoaded(AmaCarpet.ModIds.tweakermore)) {

        } if (loader.isModLoaded(AmaCarpet.ModIds.litematica)) {

        }

        return map;
    }
}
