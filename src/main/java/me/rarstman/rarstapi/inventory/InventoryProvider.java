package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryProvider<A, B, C> extends ListenerProvider {

    private InventoryTemplate inventoryTemplate;
    protected final Map<Integer, ClickableItem> clickableItems = new HashMap<>();
    protected String title = "";

    protected A onComplete;
    protected B onClose;

    public C setTitle(final String title) {
        this.title = ColorUtil.color(title);
        return (C) this;
    }

    public C setInventoryTemplate(final InventoryTemplate inventoryTemplate) {
        this.inventoryTemplate = inventoryTemplate;
        return (C) this;
    }

    public C onClose(final B onClose) {
        this.onClose = onClose;
        return (C) this;
    }

    public C onComplete(final A onComplete) {
        this.onComplete = onComplete;
        return (C) this;
    }

    public C setItem(final int slot, final ClickableItem clickableItem) {
        if(slot < 0) {
            return (C) this;
        }
        this.clickableItems.put(slot, clickableItem);
        return (C) this;
    }

    public C setItem(final Slot slot, final ClickableItem clickableItem) {
        this.setItem(slot.getSlot(), clickableItem);
        return (C) this;
    }

    public C setFirstFreeSlot(final ClickableItem clickableItem) {
        this.setItem(NumberUtil.findFirstMissingNumber(this.clickableItems.keySet()), clickableItem);
        return (C) this;
    }

    public C fill(final String field, final ClickableItem clickableItem) {
        if(this.inventoryTemplate == null) {
            return (C) this;
        }
        this.inventoryTemplate.getSlots(field)
                .stream()
                .forEach(slot -> this.setItem(slot, clickableItem));
        return (C) this;
    }

    public C fillFirstFreeField(final String field, final ClickableItem clickableItem) {
        this.inventoryTemplate.getSlots(field)
                .stream()
                .filter(var -> !this.clickableItems.containsKey(var.getSlot()))
                .findFirst()
                .ifPresent(slot -> this.setItem(slot.getSlot(), clickableItem));
        return (C) this;
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

    public abstract C build();
    public abstract void openInventory(final Player player);
    public abstract void reOpenInventory();

}
