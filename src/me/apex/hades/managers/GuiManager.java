package me.apex.hades.managers;

import me.apex.hades.data.User;
import me.apex.hades.utils.gui.Gui;
import me.apex.hades.utils.gui.GuiItem;
import me.apex.hades.utils.gui.impl.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum GuiManager {
    INSTANCE;

    public List<Gui> guis = new ArrayList<>();

    public void setup() {
        guis.add(new Homepage());
        guis.add(new CheckTypesMenu());
        guis.add(new ViolationsMenu());

        //Add Check Menus
        guis.add(new CombatMenu());
        guis.add(new MovementMenu());
        guis.add(new PacketMenu());
    }

    public boolean handleClick(User user, Inventory inventory, ItemStack clickedItem)
    {
        for(Gui gui : guis)
        {
            gui.setup();
            if(gui.inventory.getTitle().equalsIgnoreCase(inventory.getTitle()))
            {
                for(GuiItem guiItem : gui.items.keySet())
                {
                    if(guiItem.getBukkitItem().equals(clickedItem))
                    {
                        gui.onClick(user, guiItem);
                    }
                }
                return true;
            }
        }
        return false;
    }

}
