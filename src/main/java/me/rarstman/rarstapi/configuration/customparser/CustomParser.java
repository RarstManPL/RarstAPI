package me.rarstman.rarstapi.configuration.customparser;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;

public interface CustomParser<A> {

    A parse(final Class<? extends ConfigProvider> configClass, final Field field, final YamlConfiguration yamlConfiguration, final String configPath) throws CustomParserException;

}
