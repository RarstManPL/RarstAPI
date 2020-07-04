package me.rarstman.rarstapi.item;

import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.DateUtil;
import me.rarstman.rarstapi.util.MinecraftUtil;
import me.rarstman.rarstapi.util.NumberUtil;
import org.apache.commons.lang.StringUtils;
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
import java.util.stream.Collectors;

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

    public ItemBuilder(final String stringItem) {
        final String[] item = stringItem.split(" ");

        if (item.length < 1) {
            this.itemStack = new ItemStack(Material.AIR);
            this.itemMeta = this.itemStack.getItemMeta();
            return;
        }
        String materialName;
        short durability = 0;

        if (item[0].split(":").length == 0) {
            materialName = item[0].toUpperCase();
        } else {
            final String[] materialData = item[0].split(":");
            materialName = materialData[0].toUpperCase();

            if (materialData.length > 1 && NumberUtil.isNumber(materialData[1])) {
                durability = Integer.parseInt(materialData[1]) < 0 ? 0 : Short.parseShort(materialData[1]);
            }
        }

        try {
            Material.valueOf(materialName);
        } catch (final IllegalArgumentException exception) {
            this.itemStack = new ItemStack(Material.AIR);
            this.itemMeta = this.itemStack.getItemMeta();
            return;
        }
        final Material material = Material.valueOf(materialName);
        int amount = 1;

        if (item.length > 1 && NumberUtil.isNumber(item[1])) {
            amount = Integer.parseInt(item[1]) < 0 ? 1 : Integer.parseInt(item[1]);
        }
        this.itemStack = new ItemStack(material, amount, durability);
        this.itemMeta = this.itemStack.getItemMeta();

        if (item.length < 3) {
            return;
        }

        Arrays.stream(Arrays.copyOfRange(item, 2, item.length))
                .filter(option -> option.split(":", 2).length > 1)
                .map(option -> option.split(":", 2))
                .forEach(optionSplitted -> {
                    switch (optionSplitted[0].toLowerCase()) {
                        case "name": {
                            this.setName(StringUtils.join(optionSplitted[1].split("_"), " "));
                            break;
                        }
                        case "lore": {
                            this.setLore(
                                    Arrays.stream(optionSplitted[1].split("\\|"))
                                            .map(string -> StringUtils.join(string.split("_"), " "))
                                            .collect(Collectors.toList()));
                            break;
                        }
                        case "owner": {
                            this.setOwner(optionSplitted[1]);
                            break;
                        }
                        case "flags": {
                            this.addFlags(
                                    Arrays.stream(optionSplitted[1].split(","))
                                            .filter(MinecraftUtil::isItemFlag)
                                            .map(ItemFlag::valueOf)
                                            .toArray(ItemFlag[]::new)
                            );
                            break;
                        }
                        case "unbreakable": {
                            this.unbreakable();
                            break;
                        }
                        case "glowing": {
                            this.glowing();
                            break;
                        }
                        case "potions": {
                            this.addPotionEffects(
                                    Arrays.stream(optionSplitted[1].split(","))
                                            .map(potionData -> potionData.split(":"))
                                            .filter(potionData -> potionData.length > 2 && MinecraftUtil.isPotionEffectType(potionData[0]) && NumberUtil.isNumber(potionData[2]) && Integer.parseInt(potionData[2]) > 0)
                                            .map(potionData -> new PotionEffect(MinecraftUtil.getPotionEffectType(potionData[0]), DateUtil.stringToTicks(potionData[1]), Integer.parseInt(potionData[2]) - 1))
                                            .toArray(PotionEffect[]::new)
                            );
                            break;
                        }
                        case "enchants": {
                            this.addEnchantments(
                                    Arrays.stream(optionSplitted[1].split(","))
                                            .map(enchantData -> enchantData.split(":"))
                                            .filter(enchantData -> enchantData.length > 1 && MinecraftUtil.isEnchant(enchantData[0]) && NumberUtil.isNumber(enchantData[1]) && Integer.parseInt(enchantData[1]) > 0)
                                            .collect(Collectors.toMap(enchantData -> MinecraftUtil.getEnchant(enchantData[0]), enchantData -> Integer.parseInt(enchantData[1])))
                            );
                            break;
                        }
                    }
                });
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
        if (this.itemStack.getType() != Material.POTION && this.itemStack.getType() != Material.SPLASH_POTION && this.itemStack.getType() != Material.LINGERING_POTION) {
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
        this.itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this.updateItemMeta();
    }

    public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments) {
        enchantments
                .entrySet()
                .stream()
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

    public ItemBuilder clone() {
        return this.clone();
    }

}
