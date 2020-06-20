package me.rarstman.rarstapi.hook;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.hook.exception.HookInitializeException;
import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Bukkit;

public abstract class PluginHookProvider {

    private final String pluginName;
    private final Logger logger;

    public PluginHookProvider(final String pluginName) {
        this.pluginName = pluginName;
        this.logger = RarstAPIPlugin.getAPI().getAPILogger();
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public PluginHookProvider hook() {
        if(Bukkit.getPluginManager().getPlugin(this.pluginName) == null) {
            this.logger.error("No '" + this.pluginName + "' plugin on the server.");
            return null;
        }

        try {
            this.initialize();
        } catch (final HookInitializeException exception) {
            this.logger.error(exception.getMessage());
            return null;
        }
        this.logger.info("Correctly hooked into '" + this.pluginName + "' plugin.");
        return this;
    }

    protected abstract PluginHookProvider initialize() throws HookInitializeException;

}
