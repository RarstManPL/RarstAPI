package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.item.ItemBuilder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;

public class ItemBuilderParser implements CustomParser<ItemBuilder> {

    @Override
    public ItemBuilder parse(final Class<? extends ConfigProvider> configClass, final Field field, final YamlConfiguration yamlConfiguration, final String configPath) throws CustomParserException {
        if (!yamlConfiguration.isString(configPath)) {
            throw new CustomParserException("EXC");
        }
        return new ItemBuilder(yamlConfiguration.getString(configPath));
    }

}
