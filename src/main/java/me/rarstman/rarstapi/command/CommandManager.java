package me.rarstman.rarstapi.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Map<Class<? extends CommandProvider>, CommandProvider> commands = new HashMap<>();

    public static void registerCommand(final CommandProvider command) {
        if(command.register() == null) {
            return;
        }
        commands.put(command.getClass(), command);
    }

    public static void registerCommands(final CommandProvider... commands) {
        Arrays.stream(commands)
                .forEach(CommandManager::registerCommand);
    }

    public static <A extends CommandProvider> A getCommand(final Class<A> commandClass){
        return (A) commands.get(commandClass);
    }

}
