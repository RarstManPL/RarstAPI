package me.rarstman.rarstapi;

import me.rarstman.rarstapi.configuration.ConfigManager;
import me.rarstman.rarstapi.configuration.impl.RarstAPIConfig;
import me.rarstman.rarstapi.hook.HooksManager;
import me.rarstman.rarstapi.hook.impl.VaultHook;
import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class RarstAPI extends JavaPlugin {

    private Logger apiLogger;
    private RarstAPIConfig rarstAPIConfig;

    @Override
    public void onLoad() {
        this.apiLogger = new Logger(java.util.logging.Logger.getLogger("RarstAPI"));
    }

    @Override
    public void onEnable() {
        HooksManager.registerHook(new VaultHook());
        ConfigManager.registerConfig(new RarstAPIConfig());
    }

    public Logger getAPILogger() {
        return this.apiLogger;
    }

    public static RarstAPI getAPI() {
        return JavaPlugin.getPlugin(RarstAPI.class);
    }

}
