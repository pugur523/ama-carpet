// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

//#if MC >= 11900
import net.minecraft.commands.CommandBuildContext;
//#endif

public abstract class CommandTreeContext {
    //#if MC >= 11900
    public final CommandBuildContext commandBuildContext;
    //#endif

    /*
     * ---------------------
     *       Factories
     * ---------------------
     */

    protected CommandTreeContext(
            //#if MC >= 11900
            CommandBuildContext commandBuildContext
            //#endif
    ) {
        //#if MC >= 11900
        this.commandBuildContext = commandBuildContext;
        //#endif
    }

    public static Register of(
            CommandDispatcher<CommandSourceStack> dispatcher
            //#if MC >= 11900
            , CommandBuildContext commandBuildContext
            //#endif
    ) {
        return new Register(
                dispatcher
                //#if MC >= 11900
                , commandBuildContext
                //#endif
        );
    }

    public static Node of(
            ArgumentBuilder<CommandSourceStack, ?> node
            //#if MC >= 11900
            , CommandBuildContext commandBuildContext
            //#endif
    ) {
        return new Node(
                node
                //#if MC >= 11900
                , commandBuildContext
                //#endif
        );
    }

    /**
     * For mc1.19+
     * Warning: {@link #commandBuildContext} will be null
     * <p>
     * TODO: make an interface with getCommandBuildContext() method.
     * then make this method returns a impl that getCommandBuildContext() throws
     */
    public static Node ofNonContext(ArgumentBuilder<CommandSourceStack, ?> node) {
        return of(
                node
                //#if MC >= 11900
                , null
                //#endif
        );
    }

    /*
     * ---------------------
     *      Convertors
     * ---------------------
     */

    /**
     * Creates a {@link Node} context based on self's basic information
     */
    public Node node(ArgumentBuilder<CommandSourceStack, ?> node) {
        return of(
                node
                //#if MC >= 11900
                , commandBuildContext
                //#endif
        );
    }

    public static class Register extends CommandTreeContext {
        public final CommandDispatcher<CommandSourceStack> dispatcher;

        private Register(
                CommandDispatcher<CommandSourceStack> dispatcher
                //#if MC >= 11900
                , CommandBuildContext commandBuildContext
                //#endif
        ) {
            super(
                    //#if MC >= 11900
                    commandBuildContext
                    //#endif
            );
            this.dispatcher = dispatcher;
        }
    }

    /*
     * ---------------------
     * Detailed sub-classes
     * ---------------------
     */

    public static class Node extends CommandTreeContext {
        public final ArgumentBuilder<CommandSourceStack, ?> node;

        private Node(
                ArgumentBuilder<CommandSourceStack, ?> node
                //#if MC >= 11900
                , CommandBuildContext commandBuildContext
                //#endif
        ) {
            super(
                    //#if MC >= 11900
                    commandBuildContext
                    //#endif
            );
            this.node = node;
        }
    }
}
