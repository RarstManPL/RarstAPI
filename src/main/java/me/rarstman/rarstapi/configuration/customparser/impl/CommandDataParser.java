package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;

public class CommandDataParser implements CustomParser<CommandData> {

    @Override
    public CommandData parse(final Class<? extends ConfigProvider> configClass, final Field field, final YamlConfiguration yamlConfiguration, final String configPath) throws CustomParserException {
        if(!yamlConfiguration.isString(configPath + ".Name")
                || !yamlConfiguration.isList(configPath + ".Aliases")
                || !yamlConfiguration.isString(configPath + ".Usage")
                || !yamlConfiguration.isString(configPath + ".Description")
                || !yamlConfiguration.isBoolean(configPath + ".Enabled")) {
            throw new CustomParserException("EXCEPT");
        }
        return new CommandData(
                yamlConfiguration.getString(configPath + ".Name"),
                yamlConfiguration.getStringList(configPath + ".Aliases"),
                yamlConfiguration.getString(configPath + ".Description"),
                yamlConfiguration.getString(configPath + ".Usage"),
                yamlConfiguration.getBoolean(configPath + ".Enabled")
        );
    }

}
