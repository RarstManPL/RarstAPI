package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.database.DatabaseData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;

public class DatabaseDataParser implements CustomParser<DatabaseData> {

    @Override
    public DatabaseData parse(final Class<? extends ConfigProvider> configClass, final Field field, final YamlConfiguration yamlConfiguration, final String configPath) throws CustomParserException {
        if(!yamlConfiguration.isString(configPath + ".Host")
                || !yamlConfiguration.isInt(configPath + ".Port")
                || !yamlConfiguration.isString(configPath + ".User")
                || !yamlConfiguration.isString(configPath + ".Password")
                || !yamlConfiguration.isString(configPath + ".Base")) {
            throw new CustomParserException("EXC");
        }
        return new DatabaseData(
                yamlConfiguration.getString(configPath + ".Host"),
                yamlConfiguration.getInt(configPath + ".Port"),
                yamlConfiguration.getString(configPath + ".User"),
                yamlConfiguration.getString(configPath + ".Password"),
                yamlConfiguration.getString(configPath + ".Base")
        );

    }

}
