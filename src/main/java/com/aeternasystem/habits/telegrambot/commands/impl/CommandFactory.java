package com.aeternasystem.habits.telegrambot.commands.impl;

import com.aeternasystem.habits.telegrambot.commands.Command;
import com.aeternasystem.habits.telegrambot.enums.CommandName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class CommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    private final Map<CommandName, Command> commandMap;

    public CommandFactory(StartCommand startCommand, InfoCommand infoCommand, OpenMiniAppCommand openMiniAppCommand, OpenWebAppCommand openWebAppCommand) {
        commandMap = Map.of(
                CommandName.START, startCommand,
                CommandName.INFO, infoCommand,
                CommandName.APP, openMiniAppCommand,
                CommandName.WEB, openWebAppCommand
        );
    }

    public Command createCommand(CommandName commandName) {
        return Optional.ofNullable(commandMap.get(commandName))
                .orElseThrow(() -> new IllegalArgumentException("Unknown command: " + commandName));
    }
}