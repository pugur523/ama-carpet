// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client;

import net.fabricmc.api.ClientModInitializer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.apache.logging.log4j.Logger;

public class AmaCarpetClient implements ClientModInitializer {

    public static Logger LOGGER;

    @Override
    public void onInitializeClient() {
        LOGGER = AmaCarpet.LOGGER;

        ClientInitHandler.init();

        LOGGER.info("{} client has initialized! (version: {})", AmaCarpet.MOD_NAME, AmaCarpet.getVersion());
    }
}
