package me.rarstman.rarstapi.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandManager {

    private static final Map<Class<? extends CommandProvider>, CommandProvider> commandsByClass = new HashMap<>();
    private static final Map<JavaPlugin, CommandProvider> commandsByJavaPlugin = new HashMap<>();

    public static void registerCommand(final JavaPlugin javaPlugin, final CommandProvider command) {
        if(command.register() == null) {
            return;
        }
        commandsByClass.put(command.getClass(), command);
        commandsByJavaPlugin.put(javaPlugin, command);
    }

    public static void registerCommands(final JavaPlugin javaPlugin, final CommandProvider... commands) {
        Arrays.stream(commands)
                .forEach(command -> registerCommand(javaPlugin, command));
    }

    public static <A extends CommandProvider> A getCommandByClass(final Class<A> commandClass){
        return (A) commandsByClass.get(commandClass);
    }

    public static Set<CommandProvider> getCommandsByJavaPlugin(final JavaPlugin javaPlugin) {
        return commandsByJavaPlugin
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey() == javaPlugin)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

}
