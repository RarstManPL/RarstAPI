package me.rarstman.rarstapi.hook.impl;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;
import me.rarstman.rarstapi.hook.PluginHook;
import me.rarstman.rarstapi.inventory.Inventory;
import me.rarstman.rarstapi.inventory.impl.anvil.AnvilInventory;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook extends PluginHook {

    private Permission permissionProvider;
    private Economy economyProvider;

    public VaultHook() {
        super("Vault");
    }

    @Override
    public PluginHook initialize() {
        final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();
        final RegisteredServiceProvider<Permission> permissionProvider = rarstAPIProvider.getProviderServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider == null) {
            rarstAPIProvider.getProviderLogger().error("Cannot hook into Vault's Permission Provider");
            return this;
        }
        this.permissionProvider = permissionProvider.getProvider();
        final RegisteredServiceProvider<Economy> economyProvider = rarstAPIProvider.getProviderServer().getServicesManager().getRegistration(Economy.class);

        if(economyProvider == null){
            rarstAPIProvider.getProviderLogger().error("Cannot hook into Vault's Economy Provider");
            return this;
        }
        this.economyProvider = economyProvider.getProvider();
        this.setHooked(true);
        return this;
    }

    public Permission getPermissionProvider() {
        return this.permissionProvider;
    }

    public void xd(){
        Message message = new ChatMessage("Messag");
    }
}
