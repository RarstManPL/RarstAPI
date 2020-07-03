package me.rarstman.rarstapi.inventory.impl;

import me.rarstman.rarstapi.inventory.InventoryProvider;
import me.rarstman.rarstapi.inventory.Rows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ChestInventory extends InventoryProvider {

    private Inventory inventory;
    private final Rows rows;

    public ChestInventory(final Rows rows) {
        this.rows = rows;
        this.register();
    }

    @Override
    public void openInventory(final Player player) {
        if(this.inventory == null) {
            return;
        }
        player.closeInventory();
        player.openInventory(this.inventory);
    }

    @Override
    public ChestInventory build() {
        this.inventory = Bukkit.createInventory(null, rows.slots, this.title);
        this.clickableItems
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey() <= this.rows.slots - 1)
                .forEach(entrySet -> this.inventory.setItem(entrySet.getKey(), entrySet.getValue().getItemStack()));
        return this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if(event.getClickedInventory() != this.inventory) {
            return;
        }
        event.setCancelled(true);

        if(!this.clickableItems.containsKey(event.getSlot())){
            return;
        }
        if(this.clickableItems.get(event.getSlot()).isClickSet()) {
            this.clickableItems.get(event.getSlot()).onClick(event);

            if(this.onComplete != null) {
                this.onComplete.apply((Player) event.getWhoClicked(), "ClickableItem Clicked");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final InventoryCloseEvent event) {
        if(event.getInventory() != this.inventory) {
            return;
        }

        if(this.onClose != null) {
            this.onClose.accept(event);
        }

        if(this.onComplete != null) {
            this.onComplete.apply((Player) event.getPlayer(), "Inventory Closed");
        }
    }

}
