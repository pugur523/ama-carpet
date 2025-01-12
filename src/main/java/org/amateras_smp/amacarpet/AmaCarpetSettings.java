// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean cheatRestriction = false;

    @Rule(categories = { AMA, COMMAND, SURVIVAL })
    public static String commandListRestriction = "true";

    @Rule(categories = { AMA, COMMAND, SURVIVAL })
    public static String commandRestriction = "ops";

    @Rule(categories = { AMA, OPTIMIZATION, SURVIVAL })
    public static boolean disableAnimalSpawnOnChunkGen = false;

    @Rule(categories = { AMA, CREATIVE, OPTIMIZATION, SURVIVAL })
    public static boolean disableSoundEngine = false;

    //#if MC < 12100
    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endGatewayChunkLoad = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endPortalChunkLoad = false;
    //#endif

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean notifySchematicShare = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean reloadPortalTicket = false;

    @Rule(categories = { AMA })
    public static boolean requireAmaCarpetClient = false;

    @Rule(categories = { AMA }, options = {"3", "5", "10"}, strict = false)
    public static int requireAmaCarpetClientTimeoutSeconds = 5;
}
