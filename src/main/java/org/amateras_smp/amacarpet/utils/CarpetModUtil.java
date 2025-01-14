// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import carpet.CarpetSettings;
import net.minecraft.commands.CommandSourceStack;

public class CarpetModUtil {
    public static boolean canUseCommand(CommandSourceStack source, Object commandLevel) {
        //#if MC >= 11901
        return carpet.utils.CommandHelper.canUseCommand(source, commandLevel);
        //#elseif MC >= 11600
        //$$ return carpet.settings.SettingsManager.canUseCommand(source, commandLevel);
        //#else
        //$$ if (commandLevel instanceof Boolean) return (Boolean) commandLevel;
        //$$ String commandLevelString = commandLevel.toString();
        //$$ return switch (commandLevelString) {
        //$$     case "true" -> true;
        //$$     case "false" -> false;
        //$$     case "ops" -> source.hasPermission(2);
        //$$     case "0", "1", "2", "3", "4" -> source.hasPermission(Integer.parseInt(commandLevelString));
        //$$     default -> false;
        //$$ };
        //#endif
    }

    // alias of canUseCommand
    public static boolean hasEnoughPermission(CommandSourceStack source, String permissionRuleValue) {
        return canUseCommand(source, permissionRuleValue);
    }

    public static boolean canUseCarpetCommand(CommandSourceStack source) {
        // rule carpetCommandPermissionLevel is added in fabric carpet v1.4.55
        Object level =
                //#if MC >= 11700
                CarpetSettings.carpetCommandPermissionLevel;
                //#else
                //$$ 2;
        //#endif

        return canUseCommand(source, level);
    }
}
