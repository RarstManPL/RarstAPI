package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.item.ItemBuilder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class ItemBuilderParser extends CustomParser<ItemBuilder> {

    public ItemBuilderParser(final Class<? extends ConfigProvider> configClass, final Field field, final File configFile, final YamlConfiguration yamlConfiguration, final String configPath) {
        super(configClass, field, configFile, yamlConfiguration, configPath);
    }

    @Override
    public ItemBuilder parse() throws CustomParserException {
        if (!this.yamlConfiguration.isString(this.configPath)) {
            throw new CustomParserException("Value '" + this.configPath + "' in configuration '" + this.configFile.getPath() + "' isn't string. Using default or correctly parsed value... ");
        }
        return new ItemBuilder(this.yamlConfiguration.getString(this.configPath));
    }

}
