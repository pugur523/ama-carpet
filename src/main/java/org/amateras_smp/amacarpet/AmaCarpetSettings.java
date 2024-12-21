package org.amateras_smp.amacarpet;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";


    @Rule(
            categories = { AMA, SURVIVAL, OPTIMIZATION }
    )
    public static boolean disableSoundEngine = false;

    //#if MC < 12100
    @Rule(
            categories = { AMA, SURVIVAL }
    )
    public static boolean endGatewayChunkLoad = false;

    @Rule(
            categories = { AMA, SURVIVAL }
    )
    public static boolean endPortalChunkLoad = false;
    //#endif

    /*
    @Rule(
            categories = { AMA, CREATIVE }
    )
    public static boolean hopperLock = false;
    */

    @Rule(
            categories = { AMA, SURVIVAL }
    )
    public static boolean noticeSyncmaticaShared = false;

    @Rule(
            categories = { AMA, SURVIVAL }
    )
    public static boolean reloadPortalTicket = false;
}
