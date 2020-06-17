package me.rarstman.rarstapi.listener;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import me.rarstman.rarstapi.hook.HooksManager;
import me.rarstman.rarstapi.hook.PluginHook;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListenerManager {

    private static final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();

    private static final Map<Class<? extends ListenerProvider>, ListenerProvider> listeners = new HashMap<>();

    public static ListenerProvider registerListener(final ListenerProvider listenerProvider){
        return listeners.put(listenerProvider.getClass(), listenerProvider.register());
    }

    public static void registerListeners(final ListenerProvider... listeners) {
        Arrays.stream(listeners)
                .forEach(ListenerManager::registerListener);
    }

    public static Optional<ListenerProvider> getListener(final Class<? extends ListenerProvider> listenerClass){
        return Optional.of(listeners.get(listenerClass));
    }

}
