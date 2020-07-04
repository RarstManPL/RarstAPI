package me.rarstman.rarstapi.configuration.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseDisabler;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.configuration.customparser.impl.MessageParser;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;

import java.io.File;

@ParseValue(MessageParser.class)
@MessageParser.ParseMessageData(MessageParser.MessageType.CHAT)
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

    @ConfigName("ConfigurationReloaded")
    public Message configurationReloaded = new ChatMessage("Plik konfiguracyjny został przełądowany!");

    @ConfigName("ConfigurationNotCorrectlyReloaded")
    public Message configurationNotCorrectlyReloaded = new ChatMessage("Plik konfiguracyjny nie został poprawnie przeładowany. Sprawdź konsolę");

    @ConfigName("True_")
    @ParseDisabler
    public String true_ = "tak";

    @ConfigName("False_")
    @ParseDisabler
    public String false_ = "nie";

    @ConfigName("On_")
    @ParseDisabler
    public String on_ = "włączony";

    @ConfigName("Off_")
    @ParseDisabler
    public String off_ = "wyłączony";

    @ConfigName("You")
    @ParseDisabler
    public String you = "ciebie";

    @ConfigName("Lack")
    @ParseDisabler
    public String lack = "brak";

    @ConfigName("LackInformation")
    @ParseDisabler
    public String lackInformation = "brak informacji";

    @ConfigName("Unknown")
    @ParseDisabler
    public String unknown = "nieznany";

    public RarstAPIMessages() {
        super(new File(RarstAPIPlugin.getAPI().getDataFolder(), "messages.yml"), RarstAPIPlugin.getAPI().getResource("messages.yml"));
    }

}
