package org.amateras_smp.amacarpet;

//#if MC >= 11900
import carpet.api.settings.Rule;
import static carpet.api.settings.RuleCategory.*;
//#else
//$$ import carpet.settings.Rule;
//$$ import static carpet.settings.RuleCategory.*;
//#endif

public class AmaCarpetSettings {
    private static final String AMA = "AMA";

    @Rule(
            //#if MC >= 11900
            categories = { AMA, CREATIVE, COMMAND}
            //#else
            //$$ category = { AMA, CREATIVE, COMMAND },
            //$$ desc = ""
            //#endif
    )
    public static String commandHopperLock = "ops";

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = ""
            //#endif
    )
    public static boolean disableSoundEngine = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = ""
            //#endif
    )
    public static boolean serverContainerSync = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = ""
            //#endif
    )
    public static boolean reloadPortalTicket = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = ""
            //#endif
    )
    public static boolean endPortalChunkLoad = false;

    @Rule(
            //#if MC >= 11900
            categories = { AMA, SURVIVAL }
            //#else
            //$$ category = { AMA, SURVIVAL },
            //$$ desc = ""
            //#endif
    )
    public static boolean endGatewayChunkLoad = false;
}
