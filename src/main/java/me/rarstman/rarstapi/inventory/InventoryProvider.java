package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryProvider extends ListenerProvider {

    protected final Map<Integer, ClickableItem> clickableItems = new HashMap<>();
    protected String title;
    protected Inventory inventory;
    protected InventoryTemplate inventoryTemplate;
    protected Consumer<InventoryCloseEvent> onClose;
    protected boolean blockDragging = false;

    public InventoryProvider() {
        this.register();
    }

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

    public InventoryProvider blockDragging() {
        this.blockDragging = true;
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
        final Slot slot = this.inventoryTemplate.getSlots(field)
                .stream()
                .filter(var -> !this.clickableItems.containsKey(var.getSlot()))
                .findFirst()
                .get();
        if(slot != null) {
            this.setItem(slot.getSlot(), clickableItem);
        }
        return this;
    }

    public void openInventory(final Player player) {
        if(this.inventory == null) {
            return;
        }
        player.openInventory(this.inventory);
    }

    public void reOpenInventory() {
        this.inventory.getViewers()
                .stream()
                .map(humanEntity -> (Player) humanEntity)
                .forEach(this::openInventory);
    }

    public void update(final Consumer<InventoryProvider> updateConsumer) {
        updateConsumer.accept(this);
        this.build();
        this.reOpenInventory();
    }

    public Inventory getInventory() {
        return this.inventory;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if(event.getInventory() != this.inventory) {
            return;
        }

        if(this.blockDragging) {
            event.setCancelled(true);
        }

        if(!this.clickableItems.containsKey(event.getSlot())){
            return;
        }
        this.clickableItems.get(event.getSlot()).onClick(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final InventoryCloseEvent event) {
        if(this.onClose == null) {
            return;
        }

        if(event.getInventory() != this.inventory) {
            return;
        }
        this.onClose.accept(event);
    }

    public abstract InventoryProvider build();

}
