package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.configuration.customparser.impl.CommandDataParser;

import java.io.File;
import java.util.Arrays;

public class RarstAPIConfig extends ConfigProvider {

    @ConfigName("RarstAPICommand")
    @ParseValue(CommandDataParser.class)
    public CommandData rarstAPICommandData  = new CommandData("rarstapi", Arrays.asList("ra"), "Przeładowuje konfiguracje", "/rarstapi", true);

    public RarstAPIConfig() {
        super(new File(RarstAPIPlugin.getAPI().getDataFolder(), "config.yml"), RarstAPIPlugin.getAPI().getResource("config.yml"));
    }

}
