package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;

import java.io.File;
import java.io.InputStream;

public class RarstAPIConfig extends ConfigProvider {

    @ConfigName("no-permission-message")
    @ParseValue(parseType = ParseValue.ParseType.MESSAGE)
    public Message noPermission = new ChatMessage("Nie posiadasz odpowiednich uprawnień {permission}");

    @ConfigName("only-player-message")
    @ParseValue(parseType = ParseValue.ParseType.MESSAGE)
    public Message onlyPlayer = new ChatMessage("Ta komenda może zostać wykonana tylko przez graczy!");

    public RarstAPIConfig() {
        super(new File(RarstAPI.getAPI().getDataFolder(), "config.yml"), RarstAPI.getAPI().getResource("config.yml"));
    }
}
