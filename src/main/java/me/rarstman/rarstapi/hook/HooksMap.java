package me.rarstman.rarstapi.hook;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HooksMap {

    private final Map<String, PluginHook> hooks = new HashMap<>();

    public PluginHook registerHook(final PluginHook hook){
        return this.hooks.put(hook.getName(), hook);
    }

    public Optional<PluginHook> getHook(final String name){
        return Optional.of(hooks.get(name));
    }
}
