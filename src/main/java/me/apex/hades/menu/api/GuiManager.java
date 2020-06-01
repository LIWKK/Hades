package me.apex.hades.menu.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum GuiManager {
    INSTANCE;

    private final List<Gui> guis = new ArrayList();

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
