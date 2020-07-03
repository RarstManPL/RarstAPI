package me.rarstman.rarstapi.inventory.impl;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.inventory.InventoryProvider;
import me.rarstman.rarstapi.task.impl.LaterTask;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AnvilInventory extends InventoryProvider<BiFunction<Player, String, String>, Consumer<Player>, AnvilInventory> {

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
    public void reOpenInventory() {
    }

    @Override
    public AnvilInventory build() {
        this.anvilGUI = new AnvilGUI.Builder()
                .plugin(RarstAPIPlugin.getAPI())
                .title(this.title)
                .item(this.clickableItems.get(0) == null ? this.clickableItems.get(0).getItemStack() : null)
                .onClose(this.onClose)
                .onComplete(this.onComplete)
                .text(this.text);
        return this;
    }

}
