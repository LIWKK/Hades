package me.apex.hades.menu.api;

import me.apex.hades.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Gui {

    //Specifics
    public Inventory inventory;
    public Map<ItemStack, Integer> items;
    public ArrayList<Map<ItemStack, Integer>> frames;

    public Gui(String title, int size) {
        inventory = Bukkit.createInventory(null, size, ChatUtils.color(title));
        items = new HashMap<>();
        frames = new ArrayList();
    }

    //On Click
    public abstract void onClick(Player player, ItemStack item);

    //Add Item
    public void putItem(ItemStack item, int slot) {
        items.put(item, slot);
    }

    //Add Frame
    public void addFrame(Map<ItemStack, Integer> frame) {
        frames.add(frame);
    }

    //Get Item By Type
    public ItemStack getItemByType(Material material) {
        return items.keySet().stream().filter(i -> i.getType() == material).findFirst().orElse(null);
    }

    //Get Item By Name
    public ItemStack getItemByName(String title) {
        return items.keySet().stream().filter(i -> i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.color(title))).findFirst().orElse(null);
    }

    //Show Player Menu
    public void show(Player player) {
        for (ItemStack i : items.keySet()) {
            inventory.setItem(items.get(i), i);
        }
        player.openInventory(inventory);
    }

}

