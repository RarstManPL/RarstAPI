package me.rarstman.rarstapi.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerUtil {

    public static Set<Player> getNearPlayers(final Location location, final int radius) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> location.distance(player.getLocation()) <= radius)
                .collect(Collectors.toSet());
    }

}
