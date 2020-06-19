package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.hook.HooksManager;
import me.rarstman.rarstapi.hook.impl.VaultHook;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionUtil {

    private static Permission permissionProvider = HooksManager.getHook(VaultHook.class).get().getPermissionProvider();

    public static boolean hasPermission(final CommandSender commandSender, final String permission) {
        return permissionProvider != null && permissionProvider.has(commandSender, permission);
    }

    public static boolean hasPermission(final Player player, final String permission) {
        return permissionProvider != null && permissionProvider.playerHas(null, player, permission);
    }

    public static boolean hasPermission(final OfflinePlayer offlinePlayer, final String permission) {
        return permissionProvider != null && permissionProvider.playerHas(null, offlinePlayer, permission);
    }

}
