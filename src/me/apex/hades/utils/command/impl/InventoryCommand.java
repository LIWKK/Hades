package me.apex.hades.utils.command.impl;

import me.apex.hades.utils.command.Command;
import me.apex.hades.data.User;
import me.apex.hades.managers.UserManager;
import me.apex.hades.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryCommand extends Command {

    public InventoryCommand() {
        super("checkinventory");
    }

    @Override
    public void onCommand(User user, String[] args) {
        if(args.length > 0)
        {
            Player p = Bukkit.getPlayer(args[0]);
            if(p == null)
            {
                user.sendMessage("&cPlayer not online!");
                return;
            }

            User target = UserManager.INSTANCE.getUser(p.getUniqueId());
            if(user != null)
            {
                String open = ChatUtils.color(target.data.inventoryOpen ? "&aOPEN" : "&cCLOSED");
                user.sendMessage("&7Users inventory is " + open + "&7.");
            }
        }else
        {
            user.sendMessage("&cPlease enter valid arguments!");
        }
    }
}
