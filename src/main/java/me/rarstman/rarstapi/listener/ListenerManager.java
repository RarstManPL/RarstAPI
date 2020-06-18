package me.rarstman.rarstapi.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListenerManager {

    public static void registerListener(final ListenerProvider listenerProvider){
        listenerProvider.register();
    }

    public static void registerListeners(final ListenerProvider... listeners) {
        Arrays.stream(listeners)
                .forEach(ListenerManager::registerListener);
    }

}
