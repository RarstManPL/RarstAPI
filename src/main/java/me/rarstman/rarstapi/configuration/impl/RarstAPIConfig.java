package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;

import java.io.File;

public class RarstAPIConfig extends ConfigProvider {

    public RarstAPIConfig() {
        super(new File(RarstAPIPlugin.getAPI().getDataFolder(), "config.yml"), RarstAPIPlugin.getAPI().getResource("config.yml"));
    }
}
