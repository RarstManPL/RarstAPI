package me.rarstman.rarstapi.listener;

import java.util.Arrays;

public class ListenerManager {

    public static void registerListener(final ListenerProvider listenerProvider){
        listenerProvider.register();
    }

    public static void registerListeners(final ListenerProvider... listeners) {
        Arrays.stream(listeners)
                .forEach(ListenerManager::registerListener);
    }

}
