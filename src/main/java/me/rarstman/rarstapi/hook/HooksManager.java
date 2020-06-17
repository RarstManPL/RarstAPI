package me.rarstman.rarstapi.hook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HooksManager {

    private static final Map<Class<? extends PluginHook>, PluginHook> hooks = new HashMap<>();

    public static PluginHook registerHook(final PluginHook hook){
        return hooks.put(hook.getClass(), hook);
    }

    public static void registerHooks(final PluginHook... hooks) {
        Arrays.stream(hooks)
                .forEach(HooksManager::registerHook);
    }

    public static Optional<PluginHook> getHook(final Class<? extends PluginHook> hookClass){
        return Optional.of(hooks.get(hookClass));
    }

}
