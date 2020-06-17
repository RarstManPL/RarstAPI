package me.rarstman.rarstapi.item;

import me.rarstman.rarstapi.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(final Material material){
        this(material, 1, (short) 0);
    }

    public ItemBuilder(final Material material, final int amount){
        this(material, amount, (short) 0);
    }

    public ItemBuilder(final Material material, final int amount, final short durability){
        this(new ItemStack(material, amount, durability));
    }

    public ItemBuilder(final ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setName(final String name){
        this.itemMeta.setDisplayName(ColorUtil.color(name));
        return this.updateItemMeta();
    }

    public ItemBuilder setLore(final List<String> lore){
        this.itemMeta.setLore(ColorUtil.color(lore));
        return this.updateItemMeta();
    }

    public ItemBuilder setOwner(final String owner) {
        if (this.itemStack.getType() != Material.PLAYER_HEAD) {
            return this;
        }
        ((SkullMeta) this.itemMeta).setOwner(owner);
        return this.updateItemMeta();
    }

    public ItemBuilder addPotionEffects(final PotionEffect... potionEffects) {
        if (this.itemStack.getType() != Material.POTION) {
            return this;
        }
        Arrays.stream(potionEffects)
                .forEach(potionEffect -> ((PotionMeta) this.itemMeta).addCustomEffect(potionEffect, true));
        return this.updateItemMeta();
    }

    public ItemBuilder addFlags(final ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this.updateItemMeta();
    }

    public ItemBuilder unbreakable() {
        this.itemMeta.setUnbreakable(true);
        return this.updateItemMeta();
    }

    public ItemBuilder glowing() {
        this.itemStack.addEnchantment(Enchantment.DURABILITY, 1);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this.updateItemMeta();
    }

    public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments) {
        enchantments
                .entrySet()
                .forEach(entrySet -> this.itemMeta.addEnchant(entrySet.getKey(), entrySet.getValue(), true));
        return this.updateItemMeta();
    }

    public ItemBuilder updateItemMeta() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}
