package org.amateras_smp.amacarpet;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL, OPTIMIZATION }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = "disabling server side sound engine"
            //#endif
    )
    public static boolean disableSoundEngine = false;

    //#if MC < 12100
    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = "entities go through end gateway portal will load 3x3 chunk"
            //#endif
    )
    public static boolean endGatewayChunkLoad = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = "entities go through end portal will load 3x3 chunk"
            //#endif
    )
    public static boolean endPortalChunkLoad = false;
    //#endif

    @Rule(
            //#if MC >= 11900
            categories = { AMA, CREATIVE }
            //#else
            //$$ category = { AMA, CREATIVE },
            //$$ desc = "locks all of the hoppers in the server"
            //#endif
    )
    public static boolean hopperLock = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = "reload nether portal tickets when server restarts"
            //#endif
    )
    public static boolean reloadPortalTicket = false;


}
