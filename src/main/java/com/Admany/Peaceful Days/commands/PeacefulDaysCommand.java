package com.admany.peacefuldays.commands;

import com.admany.peacefuldays.config.ConfigManager;
import com.admany.peacefuldays.utils.ModLogger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class PeacefulDaysCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("peacefuldays")
                        .requires(source -> source.hasPermission(4))  // Op level permission
                        .then(Commands.literal("status")
                                .executes(PeacefulDaysCommand::getStatus))
                        .then(Commands.literal("forcepeaceful")
                                .executes(PeacefulDaysCommand::forcePeacefulDay))
                        .then(Commands.literal("devinfo")
                                .executes(PeacefulDaysCommand::showDevInfo))
        );
    }

    private static int getStatus(CommandContext<CommandSourceStack> context) {
        ServerLevel world = context.getSource().getLevel();

        context.getSource().sendSystemMessage(Component.literal("Peaceful Days Mod Status:"));
        context.getSource().sendSystemMessage(Component.literal("Peaceful Mode: " +
                ConfigManager.PEACEFUL_MODE_ENABLED.get()));
        context.getSource().sendSystemMessage(Component.literal("Mode Type: " +
                ConfigManager.PEACEFUL_MODE_TYPE.get()));

        return 1;
    }

    private static int forcePeacefulDay(CommandContext<CommandSourceStack> context) {
        // TODO: Implement logic to force a peaceful day
        context.getSource().sendSystemMessage(Component.literal("Forcing peaceful day..."));
        ModLogger.info("Peaceful day forced by admin");

        return 1;
    }

    private static int showDevInfo(CommandContext<CommandSourceStack> context) {
        if (ConfigManager.DEV_MODE.get()) {
            context.getSource().sendSystemMessage(Component.literal("Developer Information:"));
            // Add detailed diagnostics here
            context.getSource().sendSystemMessage(Component.literal("Current Mod Configuration:"));
            // Log current config values
        } else {
            context.getSource().sendSystemMessage(Component.literal("Dev mode is not enabled in config."));
        }

        return 1;
    }
}
