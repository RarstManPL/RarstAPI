package me.rarstman.rarstapi.configuration.customparser;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public abstract class CustomParser<A> {

    public final Class<? extends ConfigProvider> configClass;
    public final Field field;
    public final File configFile;
    public final YamlConfiguration yamlConfiguration;
    public final String configPath;

    public CustomParser(final Class<? extends ConfigProvider> configClass, final Field field, final File configFile, final YamlConfiguration yamlConfiguration, final String configPath) {
        this.configClass = configClass;
        this.field = field;
        this.configFile = configFile;
        this.yamlConfiguration = yamlConfiguration;
        this.configPath = configPath;
    }

    public abstract A parse() throws CustomParserException;

}
