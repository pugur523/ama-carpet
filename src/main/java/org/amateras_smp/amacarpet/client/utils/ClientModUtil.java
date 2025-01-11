package org.amateras_smp.amacarpet.client.utils;

import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class ClientModUtil {
    public static boolean isFeatureEnable(String modId, String className, String fieldName) {
        if (!FabricLoader.getInstance().isModLoaded(modId)) return false;
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
        HashMap<String, Boolean> map = new HashMap<>();


        return map;
    }
}
