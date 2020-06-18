package me.rarstman.rarstapi.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static void sendMessage(final Player player, final String message, final String... replaces) {
        player.sendMessage(ColorUtil.color(StringUtil.replace(message, replaces)));
    }

    public static void sendMessage(final CommandSender commandSender, final String message, final String... replaces) {
        commandSender.sendMessage(ColorUtil.color(StringUtil.replace(message, replaces)));
    }

    public static void broadCast(final String message, final String... replaces) {
        Bukkit.getOnlinePlayers()
                .stream()
                .forEach(player -> sendMessage(player, message, replaces));
    }

    public static void broadCast(final String permission, final String message, final String... replaces) {
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> PermissionUtil.hasPermission(player, permission))
                .forEach(player -> sendMessage(player, message, replaces));
    }

    public static void broadCast(final Location location, final int radius, final String message){
        PlayerUtil.getNearPlayers(location, radius)
                .stream()
                .forEach(player -> sendMessage(player, message));
    }
}
