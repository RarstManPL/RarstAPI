package me.rarstman.rarstapi.hook.impl;

import me.rarstman.rarstapi.hook.PluginHookProvider;
import me.rarstman.rarstapi.hook.exception.HookInitializeException;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHookProvider {

    private Permission permissionProvider;

    @Override
    public PluginHookProvider initialize() throws HookInitializeException {
        final RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (permissionProvider == null) {
            throw new HookInitializeException("Cannot hook into Vault's Permission Provider.");
        }
        this.permissionProvider = permissionProvider.getProvider();
        return this;
    }

    public Permission getPermissionProvider() {
        return this.permissionProvider;
    }

}
