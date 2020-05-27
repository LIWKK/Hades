package me.apex.hades.menu.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum GuiManager {
    INSTANCE;

    private List<Gui> guis = new ArrayList();

    public void registerGui(Gui gui) {
        guis.add(gui);
    }

    public boolean onClick(Player player, ItemStack item, Inventory inv) {
        for (Gui gui : guis) {
            if (gui.inventory.getTitle().equalsIgnoreCase(inv.getTitle())) {
                gui.onClick(player, item);
                return true;
            }
        }
        return false;
    }

}
