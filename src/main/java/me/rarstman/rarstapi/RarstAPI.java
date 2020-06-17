package me.rarstman.rarstapi;

import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class RarstAPI extends JavaPlugin implements RarstAPIProvider {

    private static RarstAPI rarstAPI;
    private RarstAPIProvider rarstAPIProvider;
    private Logger logger;

    public RarstAPI(){
        rarstAPI = this;
    }

    @Override
    public void onLoad(){
        rarstAPI = this;
        this.rarstAPIProvider = this;

        this.logger = new Logger(java.util.logging.Logger.getLogger("RarstAPI"));
    }

    public void setRarstAPIProvider(final RarstAPIProvider rarstAPIProvider){
        this.rarstAPIProvider = rarstAPIProvider;
    }

    public RarstAPIProvider getRarstAPIProvider() {
        return this.rarstAPIProvider;
    }

    public static RarstAPI getAPI() {
        return rarstAPI;
    }

    @Override
    public String getProviderName() {
        return "RarstAPI";
    }

    @Override
    public String getProviderVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public Server getProviderServer() {
        return Bukkit.getServer();
    }

    @Override
    public Logger getProviderLogger() {
        return this.logger;
    }

    @Override
    public JavaPlugin getProviderJavaPlugin() {
        return this;
    }
}
