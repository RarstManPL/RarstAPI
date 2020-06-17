package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerUtil {

    private static final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();

    public Set<Player> getNearPlayers(final Entity entity, final int radius) {
        return rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .filter(player -> entity.getLocation().distance(player.getLocation()) <= radius)
                .collect(Collectors.toSet());
    }
}
