package me.rarstman.rarstapi.command;

import me.rarstman.rarstapi.RarstAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {

    private static CommandMap commandMap = null;

    static {
        try {
            final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer().getPluginManager());
        } catch (final NoSuchFieldException | IllegalAccessException exception){
            RarstAPIPlugin.getAPI().getAPILogger().exception(exception, "Error while trying to get CommandMap.");
        }
    }

    public static void registerCommand(final CommandProvider command) {
        commandMap.register(command.getName(), (Command) command);
    }

    public static void registerCommand(final CommandProvider... commands) {
        Arrays.stream(commands)
                .forEach(CommandManager::registerCommand);
    }

}
