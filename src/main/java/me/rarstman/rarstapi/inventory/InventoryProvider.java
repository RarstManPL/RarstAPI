package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class InventoryProvider extends ListenerProvider {

    private InventoryTemplate inventoryTemplate;
    protected final Map<Integer, ClickableItem> clickableItems = new HashMap<>();
    protected String title = "";

    protected BiFunction<Player, String, String> onComplete;
    protected Consumer<InventoryCloseEvent> onClose;

    public InventoryProvider setTitle(final String title) {
        this.title = ColorUtil.color(title);
        return this;
    }

    public InventoryProvider setInventoryTemplate(final InventoryTemplate inventoryTemplate) {
        this.inventoryTemplate = inventoryTemplate;
        return this;
    }

    public InventoryProvider onClose(final Consumer<InventoryCloseEvent> onClose) {
        this.onClose = onClose;
        return this;
    }

    public InventoryProvider onComplete(final BiFunction<Player, String, String> onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    public InventoryProvider setItem(final int slot, final ClickableItem clickableItem) {
        if(slot < 0) {
            return this;
        }
        this.clickableItems.put(slot, clickableItem);
        return this;
    }

    public InventoryProvider setItem(final Slot slot, final ClickableItem clickableItem) {
        this.setItem(slot.getSlot(), clickableItem);
        return this;
    }

    public InventoryProvider setFirstFreeSlot(final ClickableItem clickableItem) {
        this.setItem(NumberUtil.findFirstMissingNumber(this.clickableItems.keySet()), clickableItem);
        return this;
    }

    public InventoryProvider fill(final String field, final ClickableItem clickableItem) {
        if(this.inventoryTemplate == null) {
            return this;
        }
        this.inventoryTemplate.getSlots(field)
                .stream()
                .forEach(slot -> this.setItem(slot, clickableItem));
        return this;
    }

    public InventoryProvider fillFirstFreeField(final String field, final ClickableItem clickableItem) {
        this.inventoryTemplate.getSlots(field)
                .stream()
                .filter(var -> !this.clickableItems.containsKey(var.getSlot()))
                .findFirst()
                .ifPresent(slot -> this.setItem(slot.getSlot(), clickableItem));
        return this;
    }

    public InventoryTemplate getInventoryTemplate() {
        return this.inventoryTemplate;
    }

    public Map<Integer, ClickableItem> getClickableItems() {
        return this.clickableItems;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract InventoryProvider build();
    public abstract void openInventory(final Player player);

}
