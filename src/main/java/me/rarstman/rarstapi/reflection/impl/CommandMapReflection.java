package me.rarstman.rarstapi.reflection.impl;

import me.rarstman.rarstapi.reflection.ReflectionProvider;
import me.rarstman.rarstapi.reflection.exception.ReflectionInitializeException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public class CommandMapReflection extends ReflectionProvider {

    private CommandMap commandMap = null;

    @Override
    protected ReflectionProvider initialize() throws ReflectionInitializeException {
        try {
            final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            this.commandMap = (CommandMap) field.get(Bukkit.getServer().getPluginManager());
        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            throw new ReflectionInitializeException("Error while trying to get commandMap");
        }
        return this;
    }

    public CommandMap getCommandMap() {
        return this.commandMap;
    }

}
