package me.rarstman.rarstapi.command;

import me.rarstman.rarstapi.configuration.ConfigProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Map<Class<? extends CommandProvider>, CommandProvider> commands = new HashMap<>();

    public static void registerCommand(final CommandProvider command) {
        commands.put(command.getClass(), command.register());
    }

    public static void registerCommand(final CommandProvider... commands) {
        Arrays.stream(commands)
                .forEach(CommandManager::registerCommand);
    }

    public static <A extends CommandProvider> A getCommand(final Class<A> commandClass){
        return (A) commands.get(commandClass);
    }

}
