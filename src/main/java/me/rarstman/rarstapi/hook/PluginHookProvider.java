package me.rarstman.rarstapi.hook;

import me.rarstman.rarstapi.hook.exception.HookInitializeException;

public interface PluginHookProvider {

    PluginHookProvider initialize() throws HookInitializeException;

}
