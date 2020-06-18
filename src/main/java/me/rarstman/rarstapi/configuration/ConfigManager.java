package me.rarstman.rarstapi.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigManager {

    private static final Map<Class<? extends ConfigProvider>, ConfigProvider> configs = new HashMap<>();

    public static void registerConfig(final ConfigProvider config){
        configs.put(config.getClass(), config.initialize());
    }

    public static void registerConfigs(final ConfigProvider... configs) {
        Arrays.stream(configs)
                .forEach(ConfigManager::registerConfig);
    }

    public static <A extends ConfigProvider> Optional<A> getConfig(final Class<A> configClass){
        return Optional.of((A) configs.get(configClass));
    }
}
