package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {

    private static final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();

    public static void sendMessage(final Player player, final String message) {
        player.sendMessage(ColorUtil.color(message));
    }

    public static void sendMessage(final CommandSender commandSender, final String message) {
        commandSender.sendMessage(ColorUtil.color(message));
    }

    public static void broadCast(final String message) {
        rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .forEach(player -> sendMessage(player, message));
    }

    public static void broadCast(final String permission, final String message) {
        rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .filter(player -> PermissionUtil.hasPermission(player, permission))
                .forEach(player -> sendMessage(player, message));
    }

    public static void broadCast(final Location location, final int radius, final String message){
        PlayerUtil.getNearPlayers(location, radius)
                .stream()
                .forEach(player -> sendMessage(player, message));
    }
}
