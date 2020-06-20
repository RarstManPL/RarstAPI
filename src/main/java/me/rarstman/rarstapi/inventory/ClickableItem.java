package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.item.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private final ItemBuilder itemBuilder;
    private Consumer<InventoryClickEvent> onClick;

    public ClickableItem(final ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public ClickableItem onClick(final Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    public void onClick(final InventoryClickEvent inventoryClickEvent) {
        this.onClick.accept(inventoryClickEvent);
    }

    public ItemStack getItemStack() {
        return this.itemBuilder.build();
    }

}
