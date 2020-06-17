package me.rarstman.rarstapi.listener;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import org.bukkit.event.Listener;

public abstract class ListenerProvider implements Listener {

    private final RarstAPIProvider rarstAPIProvider;

    public ListenerProvider() {
        this.rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();
    }

    public ListenerProvider register() {
        this.rarstAPIProvider.getProviderServer().getPluginManager().registerEvents(this, this.rarstAPIProvider.getProviderJavaPlugin());
        return this;
    }

    public <A> A getListenerInstance(final Class<A> returnType){
        return (A) this;
    }

}
