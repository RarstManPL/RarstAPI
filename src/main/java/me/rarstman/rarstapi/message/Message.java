package me.rarstman.rarstapi.message;

import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.PermissionUtil;
import me.rarstman.rarstapi.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Message  {

    public String message;

    public Message(final String message) {
        this.message = ColorUtil.color(message);
    }

    public Message(final Message message) {
        this(message.getMessage());
    }

    public Message replace(final String... replaces) {
        this.message = StringUtil.replace(this.message, replaces);
        return this;
    }

    public void broadCast(final String... replaces) {
        Bukkit.getOnlinePlayers()
                .stream()
                .forEach(player -> this.send(player, replaces));
    }

    public void broadCastPermission(final String permission, final String... replaces) {
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> PermissionUtil.hasPermission(player, permission))
                .forEach(player -> this.send(player, replaces));
    }

    public void send(final OfflinePlayer offlinePlayer, final String... replaces) {
        if(!offlinePlayer.isOnline()) {
            return;
        }
        this.send(offlinePlayer.getPlayer(), replaces);
    }

    public String getMessage() {
        return this.message;
    }

    public abstract void send(final Player player, final String... replaces);
    public abstract void send(final CommandSender commandSender, final String... replaces);

}
