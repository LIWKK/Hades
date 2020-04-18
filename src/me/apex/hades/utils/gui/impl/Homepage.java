package me.apex.hades.utils.gui.impl;

import me.apex.hades.Hades;
import me.apex.hades.data.User;
import me.apex.hades.utils.gui.Gui;
import me.apex.hades.utils.gui.GuiItem;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Homepage extends Gui {

    @Override
    public void setup() {
        inventory = Bukkit.createInventory(null, 9, "Panel");

        //Create Panes
        GuiItem firstPane = new GuiItem(" ", Material.AIR, false);
        firstPane.setItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

        GuiItem secondPane = new GuiItem(" ", Material.AIR, false);
        secondPane.setItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

        //GuiItems List
        items.put(firstPane, 0);
        items.put(new GuiItem("&cViolations", Material.BOOK, true), 2);
        items.put(new GuiItem("&cChecks", Material.IRON_SWORD, true), 4);
        items.put(new GuiItem("&cReload", Material.REDSTONE, true), 6);
        items.put(secondPane, 8);
    }

    @Override
    public void onClick(User user, GuiItem clickedItem) {
        if(clickedItem.title.equalsIgnoreCase("&cViolations"))
        {
            new ViolationsMenu().display(user.getPlayer());
        }else if(clickedItem.title.equalsIgnoreCase("&cChecks"))
        {
            new CheckTypesMenu().display(user.getPlayer());
        }else if(clickedItem.title.equalsIgnoreCase("&cReload"))
        {
            Hades.instance.reloadConfig();
            Hades.instance.prefix = ChatUtils.color(Hades.instance.getConfig().getString("prefix"));
            user.getPlayer().closeInventory();
            user.sendMessage(Hades.instance.prefix + "Successfully reloaded config!");
        }
    }

}
