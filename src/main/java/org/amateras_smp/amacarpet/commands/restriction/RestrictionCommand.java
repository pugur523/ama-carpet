// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.commands.restriction;

import carpet.utils.Translations;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.commands.AbstractCommand;
import org.amateras_smp.amacarpet.commands.CommandTreeContext;
import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.utils.CarpetModUtil;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RestrictionCommand extends AbstractCommand {
    private static final RestrictionCommand INSTANCE = new RestrictionCommand();

    public static RestrictionCommand getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerCommand(CommandTreeContext.Register context)
    {
        LiteralArgumentBuilder<CommandSourceStack> builder = literal("restriction")
                .requires((player) -> CarpetModUtil.canUseCommand(player, AmaCarpetSettings.commandRestriction))
                .then(argument("featureName", StringArgumentType.word())
                        .executes(this::getState)
                    .then(argument("allow", BoolArgumentType.bool()))
                        .executes(this::changeSetting)
                );
        context.dispatcher.register(builder);
    }

    private static String isAllowed(boolean b) {
        return b ? "allowed" : "restricted";
    }

    public int changeSetting(CommandContext<CommandSourceStack> context) {
        String featureName = StringArgumentType.getString(context, "featureName");
        boolean value = BoolArgumentType.getBool(context, "allow");
        CheatRestrictionConfig.getInstance().set(featureName, (value ? "true" : "false"));
        context.getSource().sendSystemMessage(Component.literal(String.format(Translations.tr("ama.message.restriction.changed"), featureName, isAllowed(value))));
        return Command.SINGLE_SUCCESS;
    }

    private int getState(CommandContext<CommandSourceStack> context) {
        String featureName = StringArgumentType.getString(context, "featureName");
        boolean value = CheatRestrictionConfig.getInstance().get(featureName);
        context.getSource().sendSystemMessage(Component.literal(String.format(Translations.tr("ama.message.restriction.state"), featureName, isAllowed(value))));
        return Command.SINGLE_SUCCESS;
    }
}
