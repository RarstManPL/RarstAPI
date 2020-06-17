package me.rarstman.rarstapi;

import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public interface RarstAPIProvider {

    String getProviderName();
    String getProviderVersion();
    Server getProviderServer();
    Logger getProviderLogger();
    JavaPlugin getProviderJavaPlugin();

}
