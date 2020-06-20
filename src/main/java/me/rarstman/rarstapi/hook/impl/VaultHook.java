package me.rarstman.rarstapi.hook.impl;

import me.rarstman.rarstapi.hook.PluginHookProvider;
import me.rarstman.rarstapi.hook.exception.HookInitializeException;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook extends PluginHookProvider {

    private Permission permissionProvider;

    @Override
    protected PluginHookProvider initialize() throws HookInitializeException {
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new HookInitializeException("No 'Vault' plugin on the server.");
        }
        final RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (permissionProvider == null) {
            throw new HookInitializeException("Cannot hook into 'Vault's Permission Provider'.");
        }
        this.permissionProvider = permissionProvider.getProvider();
        return this;
    }

    public Permission getPermissionProvider() {
        return this.permissionProvider;
    }

    public VaultHook() {
        super("Vault");
    }

}
