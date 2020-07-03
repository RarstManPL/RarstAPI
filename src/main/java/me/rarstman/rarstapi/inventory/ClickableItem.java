package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.item.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> onClick;

    public ClickableItem(final ItemBuilder itemBuilder) {
        this(itemBuilder.build());
    }

    public ClickableItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ClickableItem onClick(final Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    protected void onClick(final InventoryClickEvent inventoryClickEvent) {
        this.onClick.accept(inventoryClickEvent);
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

}
