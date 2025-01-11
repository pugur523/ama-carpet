// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";

    // TODO : implement this.
    // @Rule(categories = { AMA, SURVIVAL })
    // public static boolean cheatDetector = false;

    @Rule(categories = { AMA, SURVIVAL, OPTIMIZATION })
    public static boolean disableAnimalSpawnOnChunkGen = false;

    @Rule(categories = { AMA, SURVIVAL, CREATIVE, OPTIMIZATION })
    public static boolean disableSoundEngine = false;

    //#if MC < 12100
    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endGatewayChunkLoad = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endPortalChunkLoad = false;
    //#endif

    /*
    @Rule(categories = { AMA, CREATIVE })
    public static boolean hopperLock = false;
    */

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean notifyLitematicShared = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean reloadPortalTicket = false;

    @Rule(categories = { AMA })
    public static boolean requireAmaCarpetClient = false;

    @Rule(categories = { AMA }, options = {"3", "5", "10"})
    public static int requireAmaCarpetClientTimeOutSeconds = 5;
}
