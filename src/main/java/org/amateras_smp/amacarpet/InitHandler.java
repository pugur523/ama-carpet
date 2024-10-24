package org.amateras_smp.amacarpet;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.network.packet.AmaCarpetPackets;

public class InitHandler {
    public static void init() {

        AmaCarpetServer.init();

        EnvType environmentType = FabricLoader.getInstance().getEnvironmentType();
        if (environmentType== EnvType.SERVER) {
            AmaCarpetPackets.registerS2CPackets();
        }
        if (environmentType==EnvType.CLIENT) {
            AmaCarpetPackets.registerC2SPackets();
        }
    }
}
