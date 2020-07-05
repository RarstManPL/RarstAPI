package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class CommandDataParser extends CustomParser<CommandData> {

    public CommandDataParser(final Class<? extends ConfigProvider> configClass, final Field field, final File configFile, final YamlConfiguration yamlConfiguration, final String configPath) {
        super(configClass, field, configFile, yamlConfiguration, configPath);
    }

    @Override
    public CommandData parse() throws CustomParserException {
        if(!this.yamlConfiguration.isString(this.configPath + ".Name")
                || !this.yamlConfiguration.isList(this.configPath + ".Aliases")
                || !this.yamlConfiguration.isString(this.configPath + ".Usage")
                || !this.yamlConfiguration.isString(this.configPath + ".Description")
                || !this.yamlConfiguration.isBoolean(this.configPath + ".Enabled")) {
            throw new CustomParserException("Incomplete command configuration data in file '" + this.configFile.getPath() + "', path '" + this.configPath + "'. Using default or last correctly parsed value...");
        }
        return new CommandData(
                this.yamlConfiguration.getString(this.configPath + ".Name"),
                this.yamlConfiguration.getStringList(this.configPath + ".Aliases"),
                this.yamlConfiguration.getString(this.configPath + ".Description"),
                this.yamlConfiguration.getString(this.configPath + ".Usage"),
                this.yamlConfiguration.getBoolean(this.configPath + ".Enabled")
        );
    }

}
