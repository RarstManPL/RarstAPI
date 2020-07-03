package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryProvider<A, B> extends ListenerProvider {

    private InventoryTemplate inventoryTemplate;
    protected final Map<Integer, ClickableItem> clickableItems = new HashMap<>();
    protected String title = "";

    protected A onComplete;
    protected B onClose;

    public InventoryProvider setTitle(final String title) {
        this.title = ColorUtil.color(title);
        return this;
    }

    public InventoryProvider setInventoryTemplate(final InventoryTemplate inventoryTemplate) {
        this.inventoryTemplate = inventoryTemplate;
        return this;
    }

    public InventoryProvider onClose(final B onClose) {
        this.onClose = onClose;
        return this;
    }

    public InventoryProvider onComplete(final A onComplete) {
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

    public void update(final Consumer<InventoryProvider> updateConsumer) {
        updateConsumer.accept(this);
        this.build();
    }

    public abstract InventoryProvider build();
    public abstract void openInventory(final Player player);
    public abstract void reOpenInventory();

}
