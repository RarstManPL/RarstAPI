package me.rarstman.rarstapi.listener;

import me.rarstman.rarstapi.RarstAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListenerProvider implements Listener {

    public ListenerProvider register() {
        Bukkit.getPluginManager().registerEvents(this, RarstAPIPlugin.getAPI());
        return this;
    }

}
