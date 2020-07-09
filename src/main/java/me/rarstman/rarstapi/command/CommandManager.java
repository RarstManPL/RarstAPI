package me.rarstman.rarstapi.command;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.rarstman.rarstapi.util.StringUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandManager {

    private static final Map<Class<? extends CommandProvider>, CommandProvider> commandsByClass = new HashMap<>();
    private static final Multimap<JavaPlugin, CommandProvider> commandsByJavaPlugin = ArrayListMultimap.create();

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
                .get(javaPlugin)
                .stream()
                .collect(Collectors.toSet());
    }

    public static boolean isCommandByJavaPlugin(final JavaPlugin javaPlugin, final String name) {
        final String name1 = name.startsWith("/") ? name.substring(1) : name;
        return getCommandsByJavaPlugin(javaPlugin)
                .stream()
                .anyMatch(command -> command.getName().equalsIgnoreCase(name1) || StringUtil.containsIgnoreCase(command.getAliases(), name1));
    }

}
