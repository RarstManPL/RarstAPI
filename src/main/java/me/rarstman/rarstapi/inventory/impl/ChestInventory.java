package me.rarstman.rarstapi.inventory.impl;

import me.rarstman.rarstapi.inventory.InventoryProvider;
import me.rarstman.rarstapi.inventory.Rows;
import org.bukkit.Bukkit;

public class ChestInventory extends InventoryProvider {

    public final Rows rows;

    public ChestInventory(final Rows rows) {
        this.rows = rows;
    }

    @Override
    public InventoryProvider build() {
        this.inventory = Bukkit.createInventory(null, rows.columns, this.title);
        this.clickableItems
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey() <= this.rows.slots - 1)
                .forEach(entrySet -> this.inventory.setItem(entrySet.getKey(), entrySet.getValue().getItemStack()));
        return this;
    }
}
