package me.apex.hades.utils.gui.impl;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.EventHandler;
import me.apex.hades.utils.gui.Gui;
import me.apex.hades.utils.gui.GuiItem;
import me.apex.hades.managers.CheckManager;
import me.apex.hades.managers.UserManager;
import me.apex.hades.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class MovementMenu extends Gui {

    @Override
    public void setup() {
        inventory = Bukkit.createInventory(null, 54, "Movement Checks");
        items.clear();

        //Add Back Button
        for(int i = 0; i < 9; i++)
        {
            items.put(new GuiItem("&cBack", Material.STAINED_GLASS_PANE, false), i);
        }

        //Add Checks
        int count = 9;
        for(EventHandler handler : CheckManager.INSTANCE.getChecks(null))
        {
            Check check = (Check) handler;
            if(check.type == Check.CheckType.MOVEMENT)
            {
                items.put(new GuiItem(check.enabled ? "&a" + check.name : "&c" + check.name, Material.ENDER_PEARL, check.enabled ? true : false), count);
                count++;
            }
        }
    }

    @Override
    public void onClick(User user, GuiItem clickedItem) {
        if(clickedItem.title.equalsIgnoreCase("&cBack"))
        {
            new CheckTypesMenu().display(user.getPlayer());
        }else
        {
            for(User u : UserManager.INSTANCE.users)
            {
                for(Check check : u.getChecks())
                {
                    if(check.name.equalsIgnoreCase(ChatUtils.stripColor(ChatUtils.color(clickedItem.title))))
                    {
                        if(check.enabled)
                        {
                            check.enabled = false;
                        }else
                        {
                            check.enabled = true;
                        }
                        new MovementMenu().display(user.getPlayer());
                        return;
                    }
                }
            }
        }
    }

}
