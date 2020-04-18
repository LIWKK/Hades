package me.apex.hades.utils.gui;

import me.apex.hades.data.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class Gui {

    public Inventory inventory;
    public HashMap<GuiItem, Integer> items = new HashMap<>();

    public Gui() { setup(); }

    public void display(Player player)
    {
        for(GuiItem guiItem : items.keySet())
        {
            inventory.setItem(items.get(guiItem), guiItem.getBukkitItem());
        }
        player.openInventory(inventory);
    }

    public abstract void setup();

    public abstract void onClick(User user, GuiItem clickedItem);

}
