package me.rarstman.rarstapi.listener;

import me.rarstman.rarstapi.RarstAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListenerProvider implements Listener {

    public ListenerProvider register() {
        Bukkit.getPluginManager().registerEvents(this, RarstAPI.getAPI());
        return this;
    }

}
