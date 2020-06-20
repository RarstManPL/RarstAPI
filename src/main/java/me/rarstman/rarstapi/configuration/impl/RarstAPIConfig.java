package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;

import java.io.File;
import java.util.Arrays;

public class RarstAPIConfig extends ConfigProvider {

    @ConfigName("RarstAPICommandData")
    @ParseValue(parseType = ParseValue.ParseType.COMMANDDATA)
    public CommandData rarstAPICommandData  = new CommandData("rarstapi", Arrays.asList("ra"), "Przeładowuje konfiguracje", "/rarstapi", true);

    public RarstAPIConfig() {
        super(new File(RarstAPIPlugin.getAPI().getDataFolder(), "config.yml"), RarstAPIPlugin.getAPI().getResource("config.yml"));
    }

}
