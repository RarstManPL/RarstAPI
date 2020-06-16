package me.rarstman.rarstapi;

import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Server;

public interface RarstAPIProvider {

    String getProviderName();
    String getProviderVersion();
    Server getProviderServer();
    Logger getProviderLogger();

}
