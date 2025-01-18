// Copyright (c) 2025 The Ama-Carpet Authors
// This file is part of the Ama-Carpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.commands.restriction;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.commands.AbstractCommand;
import org.amateras_smp.amacarpet.commands.CommandTreeContext;
import org.amateras_smp.amacarpet.utils.CarpetModUtil;

import static net.minecraft.commands.Commands.literal;
import static org.amateras_smp.amacarpet.commands.restriction.RestrictionCommand.listAllRestrictions;

public class ListRestrictionCommand extends AbstractCommand {
    private static final ListRestrictionCommand INSTANCE = new ListRestrictionCommand();
    public static ListRestrictionCommand getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerCommand(CommandTreeContext.Register context) {
        LiteralArgumentBuilder<CommandSourceStack> builder = literal("listrestriction")
                .requires((player) -> CarpetModUtil.canUseCommand(player, AmaCarpetSettings.commandListRestriction))
                .executes(this::listAll);
        context.dispatcher.register(builder);
    }

    public int listAll(CommandContext<CommandSourceStack> context) {
        return listAllRestrictions(context);
    }
}
