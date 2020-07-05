package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.database.DatabaseData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class DatabaseDataParser extends CustomParser<DatabaseData> {

    public DatabaseDataParser(final Class<? extends ConfigProvider> configClass, final Field field, final File configFile, final YamlConfiguration yamlConfiguration, final String configPath) {
        super(configClass, field, configFile, yamlConfiguration, configPath);
    }

    @Override
    public DatabaseData parse() throws CustomParserException {
        if(!this.yamlConfiguration.isString(this.configPath + ".Host")
                || !this.yamlConfiguration.isInt(this.configPath + ".Port")
                || !this.yamlConfiguration.isString(this.configPath + ".User")
                || !this.yamlConfiguration.isString(this.configPath + ".Password")
                || !this.yamlConfiguration.isString(this.configPath + ".Base")) {
            throw new CustomParserException("Incomplete database configuration data in file '" + this.configFile.getPath() + "', path '" + this.configPath + "'. Using default or last correctly parsed value...");
        }
        return new DatabaseData(
                this.yamlConfiguration.getString(this.configPath + ".Host"),
                this.yamlConfiguration.getInt(this.configPath + ".Port"),
                this.yamlConfiguration.getString(this.configPath + ".User"),
                this.yamlConfiguration.getString(this.configPath + ".Password"),
                this.yamlConfiguration.getString(this.configPath + ".Base")
        );
    }

}
