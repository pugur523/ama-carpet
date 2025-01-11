// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import org.amateras_smp.amacarpet.network.AmaCarpetPayload;

public class InitHandler {

    public static void init() {
        AmaCarpetServer.init();
        AmaCarpetPayload.register();
    }
}
