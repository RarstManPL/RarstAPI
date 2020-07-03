package me.rarstman.rarstapi.inventory;

import me.rarstman.rarstapi.item.ItemBuilder;
import me.rarstman.rarstapi.listener.ListenerProvider;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

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

    public InventoryProvider setInventoryTemplate(final String... template) {
        return this.setInventoryTemplate(new InventoryTemplate(template));
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

    public InventoryProvider setItem(final int slot, final ItemStack itemStack) {
        return this.setItem(slot, new ClickableItem(itemStack));
    }

    public InventoryProvider setItem(final int slot, final ItemBuilder itemBuilder) {
        return this.setItem(slot, new ClickableItem(itemBuilder));
    }

    public InventoryProvider setItem(final int slot, final Material material) {
        return this.setItem(slot, new ClickableItem(material));
    }

    public InventoryProvider setItem(final Slot slot, final ClickableItem clickableItem) {
        return this.setItem(slot.getSlot(), clickableItem);
    }

    public InventoryProvider setItem(final Slot slot, final ItemStack itemStack) {
        return this.setItem(slot, new ClickableItem(itemStack));
    }

    public InventoryProvider setItem(final Slot slot, final ItemBuilder itemBuilder) {
        return this.setItem(slot, new ClickableItem(itemBuilder));
    }

    public InventoryProvider setItem(final Slot slot, final Material material) {
        return this.setItem(slot, new ClickableItem(material));
    }

    public InventoryProvider setFirstFreeSlot(final ClickableItem clickableItem) {
        return this.setItem(NumberUtil.findFirstMissingNumber(this.clickableItems.keySet()), clickableItem);
    }

    public InventoryProvider setFirstFreeSlot(final ItemStack itemStack) {
        return this.setFirstFreeSlot(new ClickableItem(itemStack));
    }

    public InventoryProvider setFirstFreeSlot(final ItemBuilder itemBuilder) {
        return this.setFirstFreeSlot(new ClickableItem(itemBuilder));
    }

    public InventoryProvider setFirstFreeSlot(final Material material) {
        return this.setFirstFreeSlot(new ClickableItem(material));
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

    public InventoryProvider fill(final String field, final ItemBuilder itemBuilder) {
        return this.fill(field, new ClickableItem(itemBuilder));
    }

    public InventoryProvider fill(final String field, final ItemStack itemStack) {
        return this.fill(field, new ClickableItem(itemStack));
    }

    public InventoryProvider fill(final String field, final Material material) {
        return this.fill(field, new ClickableItem(material));
    }

    public InventoryProvider fillFirstFreeField(final String field, final ClickableItem clickableItem) {
        this.inventoryTemplate.getSlots(field)
                .stream()
                .filter(var -> !this.clickableItems.containsKey(var.getSlot()))
                .findFirst()
                .ifPresent(slot -> this.setItem(slot.getSlot(), clickableItem));
        return this;
    }

    public InventoryProvider fillFirstFreeField(final String field, final ItemBuilder itemBuilder) {
        return this.fillFirstFreeField(field, new ClickableItem(itemBuilder));
    }

    public InventoryProvider fillFirstFreeField(final String field, final ItemStack itemStack) {
        return this.fillFirstFreeField(field, new ClickableItem(itemStack));
    }

    public InventoryProvider fillFirstFreeField(final String field, final Material material) {
        return this.fillFirstFreeField(field, new ClickableItem(material));
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
