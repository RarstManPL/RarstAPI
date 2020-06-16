package me.rarstman.rarstapi;

import me.rarstman.rarstapi.database.Database;
import me.rarstman.rarstapi.hook.HooksMap;
import me.rarstman.rarstapi.hook.impl.VaultHook;
import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class RarstAPI extends JavaPlugin implements RarstAPIProvider {

    private static RarstAPI rarstAPI;
    private RarstAPIProvider rarstAPIProvider;
    private Logger logger;

    private Database database;
    private HooksMap hooksMap = new HooksMap();

    public RarstAPI(){
        rarstAPI = this;
    }

    @Override
    public void onLoad(){
        rarstAPI = this;
        this.rarstAPIProvider = this;

        this.logger = new Logger(java.util.logging.Logger.getLogger("RarstAPI"));

        this.initialize();
    }

    public RarstAPI initialize(){
        this.hooksMap.registerHook(new VaultHook().initialize());
        return this;
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

    public HooksMap getHooksMap() {
        return this.hooksMap;
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
}
