package me.rarstman.rarstapi.hook;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.hook.exception.HookInitializeException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HooksManager {

    private static final Map<Class<? extends PluginHookProvider>, PluginHookProvider> hooks = new HashMap<>();

    public static void registerHook(final PluginHookProvider hook){
        try {
            hook.initialize();
        } catch (final HookInitializeException exception) {
            RarstAPI.getAPI().getAPILogger().error(exception.getMessage());
            return;
        }
        hooks.put(hook.getClass(), hook);
    }

    public static void registerHooks(final PluginHookProvider... hooks) {
        Arrays.stream(hooks)
                .forEach(HooksManager::registerHook);
    }

    public static <A extends PluginHookProvider> Optional<A> getHook(final Class<A> hookClass){
        return Optional.of((A) hooks.get(hookClass));
    }

}
