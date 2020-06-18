package me.rarstman.rarstapi.hook.impl;

import me.rarstman.rarstapi.hook.PluginHookProvider;
import me.rarstman.rarstapi.hook.exception.HookInitializeException;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHookProvider {

    private Permission permissionProvider;
    private Economy economyProvider;

    @Override
    public PluginHookProvider initialize() throws HookInitializeException {
        final RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (permissionProvider == null) {
            throw new HookInitializeException("Cannot hook into Vault's Permission Provider.");
        }
        this.permissionProvider = permissionProvider.getProvider();
        final RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);

        if(economyProvider == null){
            throw new HookInitializeException("Cannot hook into Vault's Economy Provider.");
        }
        this.economyProvider = economyProvider.getProvider();
        return this;
    }

    public Permission getPermissionProvider() {
        return this.permissionProvider;
    }

}
