package me.rarstman.rarstapi.inventory.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.inventory.InventoryProvider;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

public class AnvilInventory extends InventoryProvider {

    private AnvilGUI.Builder anvilGUI;
    private final String text;

    public AnvilInventory(final String text) {
        this.text = text;
    }

    @Override
    public void openInventory(final Player player) {
        player.closeInventory();
        this.anvilGUI.open(player);
    }

    @Override
    public AnvilInventory build() {
        this.anvilGUI = new AnvilGUI.Builder()
                .plugin(RarstAPIPlugin.getAPI())
                .title(this.title)
                .onComplete(this.onComplete)
                .onClose(this.onClose)
                .text(this.text);
        this.clickableItems
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey() < 3)
                .forEach(entrySet -> this.anvilGUI.setItem(entrySet.getKey(), entrySet.getValue().getItemStack()));
        return this;
    }

}
