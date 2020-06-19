package me.rarstman.rarstapi.hook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HooksManager {

    private static final Map<Class<? extends PluginHookProvider>, PluginHookProvider> hooks = new HashMap<>();

    public static void registerHook(final PluginHookProvider hook){
        if(hook.hook() == null) {
            return;
        }
        hooks.put(hook.getClass(), hook);
    }

    public static void registerHooks(final PluginHookProvider... hooks) {
        Arrays.stream(hooks)
                .forEach(HooksManager::registerHook);
    }

    public static <A extends PluginHookProvider> A getHook(final Class<A> hookClass){
        return (A) hooks.get(hookClass);
    }

}
