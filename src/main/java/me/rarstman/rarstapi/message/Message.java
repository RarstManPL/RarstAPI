package me.rarstman.rarstapi.message;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.PermissionUtil;
import me.rarstman.rarstapi.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Message  {

    public String message;
    private final RarstAPIProvider rarstAPIProvider;

    public Message(final String message) {
        this.message = ColorUtil.color(message);
        this.rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();
    }

    public Message(final Message message) {
        this(message.getMessage());
    }

    public Message replace(final String... replaces) {
        this.message = StringUtil.replace(this.message, replaces);
        return this;
    }

    public void broadCast() {
        this.rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .forEach(this::send);
    }

    public void broadCast(final String permission) {
        this.rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .filter(player -> PermissionUtil.hasPermission(player, permission))
                .forEach(this::send);
    }

    public String getMessage() {
        return this.message;
    }

    public abstract void send(final Player player, final String... replaces);
    public abstract void send(final CommandSender commandSender, final String... replaces);
}
