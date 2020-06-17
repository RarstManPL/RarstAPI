package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerUtil {

    private static final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();

    public static Set<Player> getNearPlayers(final Location location, final int radius) {
        return rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .filter(player -> location.distance(player.getLocation()) <= radius)
                .collect(Collectors.toSet());
    }
}
