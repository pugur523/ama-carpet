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
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.amateras_smp.amacarpet.commands.AbstractCommand;
import org.amateras_smp.amacarpet.commands.CommandTreeContext;
import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.utils.CarpetModUtil;

import java.util.ArrayList;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RestrictionCommand extends AbstractCommand {
    private static final RestrictionCommand INSTANCE = new RestrictionCommand();
    private static final ArrayList<String> FEATURE_SUGGESTIONS = new ArrayList<>();

    public static RestrictionCommand getInstance()
    {
        return INSTANCE;
    }

    private static final SuggestionProvider<CommandSourceStack> FEATURE_NAME_SUGGESTIONS =
            (CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) -> {
                for (String feature : FEATURE_SUGGESTIONS) {
                    if (feature.startsWith(builder.getRemaining())) {
                        builder.suggest(feature);
                    }
                }
                return builder.buildFuture();
            };

    @Override
    public void registerCommand(CommandTreeContext.Register context) {
        initializeFeatureSuggestions();
        LiteralArgumentBuilder<CommandSourceStack> builder = literal("restriction")
                .requires((player) -> CarpetModUtil.canUseCommand(player, AmaCarpetSettings.commandRestriction))
                .then(argument("featureName", StringArgumentType.word())
                    .suggests(FEATURE_NAME_SUGGESTIONS)
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

        if (!FEATURE_SUGGESTIONS.contains(featureName)) {
            context.getSource().sendSystemMessage(Component.literal(Translations.tr("ama.message.restriction.invalid")).withStyle(ChatFormatting.RED));
            return 0;
        }

        boolean value = BoolArgumentType.getBool(context, "allow");
        CheatRestrictionConfig.getInstance().set(featureName, (value ? "true" : "false"));
        context.getSource().sendSystemMessage(Component.literal(String.format(Translations.tr("ama.message.restriction.changed"), featureName, isAllowed(value))).withStyle(ChatFormatting.YELLOW));
        return Command.SINGLE_SUCCESS;
    }

    private int getState(CommandContext<CommandSourceStack> context) {
        String featureName = StringArgumentType.getString(context, "featureName");

        if (!FEATURE_SUGGESTIONS.contains(featureName)) {
            context.getSource().sendSystemMessage(Component.literal(Translations.tr("ama.message.restriction.invalid")).withStyle(ChatFormatting.RED));
            return 0;
        }
        boolean value = CheatRestrictionConfig.getInstance().get(featureName);
        context.getSource().sendSystemMessage(Component.literal(String.format(Translations.tr("ama.message.restriction.state"), featureName, isAllowed(value))).withStyle(ChatFormatting.AQUA));
        return Command.SINGLE_SUCCESS;
    }

    private static void initializeFeatureSuggestions() {
        FEATURE_SUGGESTIONS.clear();
        FEATURE_SUGGESTIONS.addAll(ClientModUtil.tweakerooFeaturesWatchList);
        FEATURE_SUGGESTIONS.addAll(ClientModUtil.tweakerooYeetsWatchList);
        FEATURE_SUGGESTIONS.addAll(ClientModUtil.tweakermoreWatchList);
        FEATURE_SUGGESTIONS.addAll(ClientModUtil.litematicaWatchList);
    }
}
