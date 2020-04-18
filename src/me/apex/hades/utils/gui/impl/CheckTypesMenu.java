package me.apex.hades.utils.gui.impl;

import me.apex.hades.data.User;
import me.apex.hades.utils.gui.Gui;
import me.apex.hades.utils.gui.GuiItem;
import me.apex.hades.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class CheckTypesMenu extends Gui {

    @Override
    public void setup() {
        inventory = Bukkit.createInventory(null, 9, "Check Types");

        //Create Panes
        GuiItem firstPane = new GuiItem("&cBack", Material.AIR, false);
        firstPane.setItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

        GuiItem secondPane = new GuiItem("&cBack", Material.AIR, false);
        secondPane.setItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

        //GuiItems List
        items.put(firstPane, 0);
        items.put(new GuiItem("&cCombat", Material.IRON_SWORD, false), 2);
        items.put(new GuiItem("&cMovement", Material.DIAMOND_BOOTS, false), 4);
        items.put(new GuiItem("&cPacket", Material.BOOK_AND_QUILL, false), 6);
        items.put(secondPane, 8);
    }

    @Override
    public void onClick(User user, GuiItem clickedItem) {
        if(clickedItem.title.equalsIgnoreCase("&cBack"))
        {
            new Homepage().display(user.getPlayer());
        }else if(clickedItem.title.equalsIgnoreCase("&cCombat"))
        {
            new CombatMenu().display(user.getPlayer());
        }else if(clickedItem.title.equalsIgnoreCase("&cMovement"))
        {
            new MovementMenu().display(user.getPlayer());
        }else if(clickedItem.title.equalsIgnoreCase("&cPacket"))
        {
            new PacketMenu().display(user.getPlayer());
        }
    }

}
