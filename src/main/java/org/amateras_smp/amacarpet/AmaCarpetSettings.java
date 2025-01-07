package org.amateras_smp.amacarpet;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";


    @Rule(categories = { AMA, SURVIVAL })
    public static boolean cheatDetector = false;

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
}
