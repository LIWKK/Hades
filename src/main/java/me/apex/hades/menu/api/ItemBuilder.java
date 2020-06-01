package me.apex.hades.menu.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.apex.hades.utils.ChatUtils;

public class ItemBuilder {

    //Specifications
    private final String title;
    private final Material material;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchantments;

    //ItemStack
    private ItemStack itemStack;

    //Constructor
    public ItemBuilder(String title, Material material) {
        this.title = title;
        this.material = material;
        lore = new ArrayList();
        enchantments = new HashMap();
    }

    //Add Lore
    public void addLore(String in) {
        lore.add(ChatUtils.color(in));
    }

    //Build Item
    public ItemStack getBukkitItem() {
        ItemStack raw = new ItemStack(material);
        ItemMeta meta = raw.getItemMeta();
        meta.setDisplayName(ChatUtils.color(title));
        meta.setLore(lore);
        raw.setItemMeta(meta);
        for (Enchantment e : enchantments.keySet())
            meta.addEnchant(e, enchantments.get(e), true);
        return raw;
    }

    //Potion Builder
    public static ItemStack buildPotion(String title, PotionType potionType, int level, boolean splash) {
        ItemStack itemStack = new ItemStack(Material.POTION);
        Potion potion = new Potion(potionType);
        potion.setLevel(level);
        potion.setSplash(splash);
        potion.apply(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtils.color(title));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}

