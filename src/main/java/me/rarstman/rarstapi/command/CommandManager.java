package me.rarstman.rarstapi.command;

import java.util.Arrays;

public class CommandManager {

    public static void registerCommand(final CommandProvider command) {
        command.register();
    }

    public static void registerCommand(final CommandProvider... commands) {
        Arrays.stream(commands)
                .forEach(CommandManager::registerCommand);
    }

}
