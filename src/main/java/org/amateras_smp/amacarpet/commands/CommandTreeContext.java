package org.amateras_smp.amacarpet.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

//#if MC >= 11900
//$$ import net.minecraft.command.CommandRegistryAccess;
//#endif

public abstract class CommandTreeContext
{
    //#if MC >= 11900
    //$$ public final CommandRegistryAccess commandBuildContext;
    //#endif

    /*
     * ---------------------
     *       Factories
     * ---------------------
     */

    protected CommandTreeContext(
            //#if MC >= 11900
            //$$ CommandRegistryAccess commandBuildContext
            //#endif
    )
    {
        //#if MC >= 11900
        //$$ this.commandBuildContext = commandBuildContext;
        //#endif
    }

    public static Register of(
            CommandDispatcher<ServerCommandSource> dispatcher
            //#if MC >= 11900
            //$$ , CommandRegistryAccess commandBuildContext
            //#endif
    )
    {
        return new Register(
                dispatcher
                //#if MC >= 11900
                //$$ , commandBuildContext
                //#endif
        );
    }

    public static Node of(
            ArgumentBuilder<ServerCommandSource, ?> node
            //#if MC >= 11900
            //$$ , CommandRegistryAccess commandBuildContext
            //#endif
    )
    {
        return new Node(
                node
                //#if MC >= 11900
                //$$ , commandBuildContext
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
    public static Node ofNonContext(ArgumentBuilder<ServerCommandSource, ?> node)
    {
        return of(
                node
                //#if MC >= 11900
                //$$ , null
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
    public Node node(ArgumentBuilder<ServerCommandSource, ?> node)
    {
        return of(
                node
                //#if MC >= 11900
                //$$ , commandBuildContext
                //#endif
        );
    }

    public static class Register extends CommandTreeContext
    {
        public final CommandDispatcher<ServerCommandSource> dispatcher;

        private Register(
                CommandDispatcher<ServerCommandSource> dispatcher
                //#if MC >= 11900
                //$$ , CommandRegistryAccess commandBuildContext
                //#endif
        )
        {
            super(
                    //#if MC >= 11900
                    //$$ commandBuildContext
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

    public static class Node extends CommandTreeContext
    {
        public final ArgumentBuilder<ServerCommandSource, ?> node;

        private Node(
                ArgumentBuilder<ServerCommandSource, ?> node
                //#if MC >= 11900
                //$$ , CommandRegistryAccess commandBuildContext
                //#endif
        )
        {
            super(
                    //#if MC >= 11900
                    //$$ commandBuildContext
                    //#endif
            );
            this.node = node;
        }
    }
}
