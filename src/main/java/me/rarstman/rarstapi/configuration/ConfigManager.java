package me.rarstman.rarstapi.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Map<Class<? extends ConfigProvider>, ConfigProvider> configs = new HashMap<>();

    public static void registerConfig(final ConfigProvider config){
        configs.put(config.getClass(), config.initialize());
    }

    public static void registerConfigs(final ConfigProvider... configs) {
        Arrays.stream(configs)
                .forEach(ConfigManager::registerConfig);
    }

    public static <A extends ConfigProvider> A getConfig(final Class<A> configClass){
        return (A) configs.get(configClass);
    }
}
