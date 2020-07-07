package me.rarstman.rarstapi.util;

import me.rarstman.rarstapi.hook.HooksManager;
import me.rarstman.rarstapi.hook.impl.VaultHook;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionUtil {

    private static Permission permissionProvider = HooksManager.getHook(VaultHook.class) == null ? null : HooksManager.getHook(VaultHook.class).getPermissionProvider();

    public static boolean hasPermission(final CommandSender commandSender, final String permission) {
        return permission == null || permissionProvider != null && permissionProvider.has(commandSender, permission);
    }

    public static boolean hasPermission(final Player player, final String permission) {
        return permission == null || permissionProvider != null && permissionProvider.playerHas(player, permission);
    }

    public static boolean hasPermission(final OfflinePlayer offlinePlayer, final String permission) {
        return permission == null || permissionProvider != null && permissionProvider.playerHas(null, offlinePlayer, permission);
    }

    public static String getPrimaryGroup(final Player player) {
        return permissionProvider == null ? null : permissionProvider.getPrimaryGroup(player);
    }

    public static String getPrimaryGroup(final OfflinePlayer offlinePlayer) {
        return permissionProvider == null ? null : permissionProvider.getPrimaryGroup(null, offlinePlayer);
    }

    public static String[] getGroups(final Player player) {
        return permissionProvider == null ? null : permissionProvider.getPlayerGroups(player);
    }

    public static String[] getGroups(final OfflinePlayer offlinePlayer) {
        return permissionProvider == null ? null : permissionProvider.getPlayerGroups(null, offlinePlayer);
    }

    public static boolean inGroup(final Player player, final String group) {
        return permissionProvider != null && permissionProvider.playerInGroup(player, group);
    }

    public static boolean inGroup(final OfflinePlayer offlinePlayer, final String group) {
        return permissionProvider != null && permissionProvider.playerInGroup(null, offlinePlayer, group);
    }

    public static boolean hasGroupPermission(final String group, final String permission) {
        return permission == null || permissionProvider != null && permissionProvider.groupHas((World) null, group, permission);
    }

}
