package org.amateras_smp.amacarpet;

import org.amateras_smp.amacarpet.network.AmaCarpetPayload;

public class InitHandler {

    public static void init() {
        AmaCarpetServer.init();
        AmaCarpetPayload.register();
    }
}
