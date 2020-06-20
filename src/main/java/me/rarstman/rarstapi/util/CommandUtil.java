package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.command.CommandProvider;
import me.rarstman.rarstapi.logger.Logger;
import me.rarstman.rarstapi.reflection.ReflectionManager;
import me.rarstman.rarstapi.reflection.impl.CommandMapReflection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.Arrays;

public class CommandUtil {

    private static final CommandMap commandMap = ReflectionManager.getReflection(CommandMapReflection.class) == null ? null : ReflectionManager.getReflection(CommandMapReflection.class).getCommandMap();
    private static final Logger logger = RarstAPIPlugin.getAPI().getAPILogger();

    public static boolean register(final CommandProvider commandProvider) {
        if(!commandProvider.isEnabled()) {
            logger.info("Registration of the '" + commandProvider.getClass().getCanonicalName() + "' command was skipped due to disable.");
            return false;
        }
        if(commandMap == null) {
            logger.error("Cannot register command '" + commandProvider.getClass().getCanonicalName() + "' due to 'commandMap''s lack.");
            return false;
        }
        commandMap.register(commandProvider.getName(), (Command) commandProvider);
        logger.info("Correctly registered command '" + commandProvider.getClass().getCanonicalName() + "'.");
        return true;
    }

    public static void register(final CommandProvider... commandProviders) {
        Arrays.stream(commandProviders)
                .forEach(CommandUtil::register);
    }

    public static boolean register(final Command command) {
        if(commandMap == null) {
            logger.error("Cannot register command '" + command.getName() + "' due to 'commandMap''s lack.");
            return false;
        }
        commandMap.register(command.getName(), command);
        logger.info("Correctly registered command '" + command.getClass().getCanonicalName() + "'.");
        return true;
    }

    public static void register(final Command... commands) {
        Arrays.stream(commands)
                .forEach(CommandUtil::register);
    }

}
