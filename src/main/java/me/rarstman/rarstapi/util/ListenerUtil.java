package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class ListenerUtil {

    private static final RarstAPIPlugin rarstAPIPlugin = RarstAPIPlugin.getAPI();
    private static final Logger logger = RarstAPIPlugin.getAPI().getAPILogger();

    public static void register(final ListenerProvider listenerProvider) {
        Bukkit.getPluginManager().registerEvents((Listener) listenerProvider, rarstAPIPlugin);
        logger.info("Correctly registered listener " + listenerProvider.getClass().getCanonicalName() + ".");
    }

    public static void register(final ListenerProvider... listenerProviders) {
        Arrays.stream(listenerProviders)
                .forEach(ListenerUtil::register);
    }

    public static void register(final Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, rarstAPIPlugin);
        logger.info("Correctly registered listener " + listener.getClass().getCanonicalName() + ".");
    }

    public static void register(final Listener... listeners) {
        Arrays.stream(listeners)
                .forEach(ListenerUtil::register);
    }

}
