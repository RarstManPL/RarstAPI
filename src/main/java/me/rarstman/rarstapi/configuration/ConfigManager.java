package me.rarstman.rarstapi.configuration;

import me.rarstman.rarstapi.configuration.customparser.CustomParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigManager {

    private static final Map<Class<? extends ConfigProvider>, ConfigProvider> configs = new HashMap<>();
    private static final Map<Class<? extends CustomParser>, CustomParser> customParsers = new HashMap<>();

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

    public static void registerCustomParser(final CustomParser customParser) {
        customParsers.put(customParser.getClass(), customParser);
    }

    public static void registerCustomParsers(final CustomParser... customParsers) {
        Arrays.stream(customParsers)
                .forEach(ConfigManager::registerCustomParser);
    }

    public static <A extends CustomParser> Optional<A> getCustomParser(final Class<A> customParserClass) {
        return Optional.of((A) customParsers.get(customParserClass));
    }

}
