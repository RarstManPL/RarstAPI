package me.rarstman.rarstapi.item;

import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

            if (materialData.length > 1 && StringUtils.isNumeric(materialData[1])) {
                durability = Integer.valueOf(materialData[1]) < 0 ? 0 : Short.valueOf(materialData[1]);
            }
        }

        if (Material.valueOf(materialName) == null) {
            this.itemStack = new ItemStack(Material.AIR);
            this.itemMeta = this.itemStack.getItemMeta();
            return;
        }
        final Material material = Material.valueOf(materialName);
        int amount = 1;

        if (item.length > 1 && StringUtils.isNumeric(item[1])) {
            amount = Integer.valueOf(item[1]) < 0 ? 1 : Integer.valueOf(item[1]);
        }
        this.itemStack = new ItemStack(material, amount, durability);
        this.itemMeta = this.itemStack.getItemMeta();

        if (item.length < 3) {
            return;
        }

        Arrays.stream(Arrays.copyOfRange(item, 2, item.length))
                .filter(option -> option.split(":").length > 1)
                .map(option -> option.split(":"))
                .forEach(optionSplitted -> {
                    switch (optionSplitted[0].toLowerCase()) {
                        case "name": {
                            this.setName(optionSplitted[1]);
                            break;
                        }
                        case "lore": {
                            Arrays.asList(optionSplitted[1].split("|"))
                                    .stream()
                                    .map(string -> StringUtils.join(string.split("_"), " "))
                                    .collect(Collectors.toList())
                                    .forEach(str -> System.out.println(str));
                            this.setLore(
                                    Arrays.asList(optionSplitted[1].split("|"))
                                            .stream()
                                            .map(string -> StringUtils.join(string.split("_"), " "))
                                            .collect(Collectors.toList())
                            );
                            break;
                        }
                        case "owner": {
                            this.setOwner(optionSplitted[1]);
                            break;
                        }
                        case "flags": {
                            this.addFlags(
                                    Arrays.asList(optionSplitted[1].split(","))
                                            .stream()
                                            .filter(itemFlag -> ItemFlag.valueOf(itemFlag.toUpperCase()) != null)
                                            .map(itemFlag -> ItemFlag.valueOf(itemFlag.toUpperCase()))
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
                                            .filter(potionData -> PotionEffectType.getByName(potionData[0]) != null && StringUtils.isNumeric(potionData[2]))
                                            .map(potionData -> new PotionEffect(PotionEffectType.getByName(potionData[0].toUpperCase()), ((Long) DateUtil.stringToMills(potionData[1])).intValue(), Integer.valueOf(potionData[2])))
                                            .toArray(PotionEffect[]::new)
                            );
                            break;
                        }
                        case "enchants": {
                            this.addEnchantments(
                                    Arrays.stream(optionSplitted[1].split(","))
                                            .map(enchantData -> enchantData.split(":"))
                                            .filter(enchantData -> Enchantment.getByKey(NamespacedKey.minecraft(enchantData[0].toUpperCase())) != null && StringUtils.isNumeric(enchantData[1]))
                                            .collect(Collectors.toMap(enchantData -> Enchantment.getByKey(NamespacedKey.minecraft(enchantData[0].toUpperCase())), enchantData -> Integer.valueOf(enchantData[1])))
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
                .forEach(entrySet -> this.itemMeta.addEnchant(entrySet.getKey(), entrySet.getValue(), false));
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
