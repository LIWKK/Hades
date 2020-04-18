package me.apex.hades.utils.gui;

import me.apex.hades.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiItem {

    public String title;
    private List<String> lore = new ArrayList<>();
    private Material itemMaterial;
    private boolean enchanted;
    private ItemStack itemStack;

    public GuiItem(String title, Material itemMaterial, boolean enchanted)
    {
        this.title = title;
        this.itemMaterial = itemMaterial;
        this.enchanted = enchanted;
    }

    public void addLore(String lore)
    {
        this.lore.add(ChatUtils.color(lore));
    }

    public void setEnchanted(boolean enchanted)
    {
        this.enchanted = enchanted;
    }

    public ItemStack getBukkitItem()
    {
        if(itemStack == null)
        {
            ItemStack menuItem = new ItemStack(itemMaterial);
            ItemMeta itemMeta = menuItem.getItemMeta();
            itemMeta.setDisplayName(ChatUtils.color(title));
            itemMeta.setLore(lore);
            if(enchanted)
            {
                itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 0, false);
            }
            menuItem.setItemMeta(itemMeta);
            return menuItem;
        }else
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatUtils.color(title));
            itemMeta.setLore(lore);
            if(enchanted)
            {
                itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 0, false);
            }
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
    }

    public void setItemStack(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

}
