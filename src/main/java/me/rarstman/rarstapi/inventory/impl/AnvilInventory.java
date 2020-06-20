package me.rarstman.rarstapi.inventory.impl;

import me.rarstman.rarstapi.inventory.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class AnvilInventory extends InventoryProvider {

    private final Integer repairCost;

    public AnvilInventory(final Integer repairCost) {
        this.repairCost = repairCost;
    }

    @Override
    public InventoryProvider build() {
        this.inventory = Bukkit.createInventory(null, InventoryType.ANVIL, this.title);
        this.clickableItems
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey() <= 2)
                .forEach(entrySet -> this.inventory.setItem(entrySet.getKey(), entrySet.getValue().getItemStack()));

        if(this.repairCost != null) {
            ((org.bukkit.inventory.AnvilInventory) this.inventory).setRepairCost(this.repairCost);
        }
        return this;
    }

}
