package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;

import java.io.File;

@ParseValue(parseType = ParseValue.ParseType.MESSAGE)
public class RarstAPIMessages extends ConfigProvider {

    @ConfigName("NoPermission")
    public Message noPermission = new ChatMessage("Nie posiadasz odpowiednich uprawnień! {PERMISSION}");

    @ConfigName("OnlyPlayer")
    public Message onlyPlayer = new ChatMessage("To polecenie przeznaczone jest jedynie dla graczy!");

    @ConfigName("BadUsage")
    public Message badUsage = new ChatMessage("Złe użycie. Poprawne: {usage}.");

    @ConfigName("PlayerNotExistOrConsole")
    public Message playerNotExistOrConsole = new ChatMessage("Podany gracz nie istnieje, jest offline lub jesteś konsolą i próbujesz wykonać polecenie bez podania gracza");

    @ConfigName("PlayerNotExist")
    public Message playerNotExist = new ChatMessage("Podany gracz nie istnieje lub jest offline!");

    @ConfigName("TurnOptionNotExist")
    public Message turnOptionNotExist = new ChatMessage("Podana opcja przełączenia nie istnieje!");

    @ConfigName("True")
    public Message true_ = new ChatMessage("tak");

    @ConfigName("False")
    public Message false_ = new ChatMessage("nie");

    @ConfigName("On")
    public Message on = new ChatMessage("włączony");

    @ConfigName("Off")
    public Message off = new ChatMessage("wyłączony");

    @ConfigName("You")
    public Message you = new ChatMessage("ciebie");

    @ConfigName("Lack")
    public Message lack = new ChatMessage("brak");

    @ConfigName("LackInformation")
    public Message lackInformation = new ChatMessage("brak informacji");

    @ConfigName("Unknown")
    public Message unknown = new ChatMessage("nieznany");

    public RarstAPIMessages() {
        super(new File(RarstAPIPlugin.getAPI().getDataFolder(), "config.yml"), RarstAPIPlugin.getAPI().getResource("config.yml"));
    }
}
