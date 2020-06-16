package me.rarstman.rarstapi.message;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Message {

    public String message;
    public final RarstAPIProvider rarstAPIProvider;

    public Message(final String message) {
        this.message = message;
        this.rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();
    }

    public Message color() {
        this.message = ColorUtil.color(this.message);
        return this;
    }

    public Message replace(final String... replaces) {
        this.message = StringUtil.replace(this.message, replaces);
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public abstract void send(final CommandSender commandSender);
    public abstract void send(final Player player);
    public abstract void broadCast();
    public abstract void broadCast(final String permission);

}
