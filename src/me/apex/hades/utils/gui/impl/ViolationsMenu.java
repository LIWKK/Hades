package me.apex.hades.utils.gui.impl;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.EventHandler;
import me.apex.hades.utils.HastebinUtil;
import me.apex.hades.utils.gui.Gui;
import me.apex.hades.utils.gui.GuiItem;
import me.apex.hades.managers.UserManager;
import me.apex.hades.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.HashMap;

public class ViolationsMenu extends Gui {

    @Override
    public void setup() {
        inventory = Bukkit.createInventory(null, 54, "Violations");
        int place = 0;
        for(User user : UserManager.INSTANCE.users)
        {
            HashMap<String, Integer> violated = new HashMap();
            for(EventHandler handler : user.handlers)
            {
                if(handler instanceof Check)
                {
                    if(((Check)handler).violations.size() > 0)
                    {
                        violated.put(((Check)handler).name, ((Check)handler).violations.size());
                    }
                }
            }
            if(violated.size() > 0)
            {
                GuiItem item = new GuiItem(ChatUtils.color("&c" + user.getPlayer().getName()), Material.REDSTONE, false);
                ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta headMeta = (SkullMeta) playerhead.getItemMeta();
                headMeta.setOwner(user.getPlayer().getName());
                playerhead.setItemMeta(headMeta);
                item.addLore(ChatUtils.color("&aChecks violated:"));
                for(String s : violated.keySet())
                {
                    item.addLore(ChatUtils.color("&7* &c" + s + " &7[&c" + violated.get(s) + "&7]"));
                }

                item.addLore(" ");
                item.addLore(ChatUtils.color("&aClick for Hastebin log"));
                item.setItemStack(playerhead);
                items.put(item, place);
            }
            place++;
        }
    }

    @Override
    public void display(Player player)
    {
        setup();
        for(GuiItem guiItem : items.keySet())
        {
            inventory.setItem(items.get(guiItem), guiItem.getBukkitItem());
        }
        player.openInventory(inventory);
    }

    @Override
    public void onClick(User user, GuiItem clickedItem) {
        Player player = Bukkit.getPlayer(ChatColor.stripColor(clickedItem.title));

        User targetUser = UserManager.INSTANCE.getUser(player.getUniqueId());

        HashMap<String, Integer> violated = new HashMap();
        for(EventHandler handler : user.handlers)
        {
            if(handler instanceof Check) {
                if (((Check) handler).violations.size() > 0)
                    violated.put(((Check) handler).name, ((Check) handler).violations.size());
            }
        }

        StringBuilder end = new StringBuilder("Hades AntiCheat Logs for " + targetUser.getPlayer().getName());

        if(violated.size() > 0) {
            for(String s : violated.keySet())
            {
                end.append("\n").append("* ").append(s).append(" [").append(violated.get(s)).append("]");
            }

            String url = HastebinUtil.uploadPaste(end.toString());
            user.sendMessage(String.format("&aUploaded logs for %s %s", targetUser.getPlayer().getName(), url));
        } else {
            user.sendMessage("&cThere are no logs for this user!");
        }
    }

}
