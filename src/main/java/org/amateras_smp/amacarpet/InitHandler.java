package org.amateras_smp.amacarpet;

import org.amateras_smp.amacarpet.network.AmaCarpetPacketPayload;

public class InitHandler {

    public static void init() {
        AmaCarpetServer.init();
        AmaCarpetPacketPayload.register();
    }
}
