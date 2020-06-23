package me.apex.hades.command.impl;

import me.apex.hades.HadesPlugin;
import me.apex.hades.command.CommandAdapter;
import me.apex.hades.command.UserInput;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import org.bukkit.entity.Player;

public class HadesCommand extends CommandAdapter {
    @Override
    public boolean onCommand(Player player, UserInput input) {
        User user = UserManager.getUser(player);
        if (input.label().equalsIgnoreCase("hades")) {
            if (input.args().length > 0) {
                if (input.args()[0].equalsIgnoreCase("alerts")) {
                    user.setAlerts(!user.isAlerts());
                    user.sendMessage("%prefix%&7Your alerts have been toggled " + (user.isAlerts() ? "&aon" : "&coff") + "&7.");
                }
                if (input.args()[0].equalsIgnoreCase("info")) {
                    if (input.args().length > 1) {
                        User target = UserManager.getUser(input.args()[1]);
                        if (target != null) {
                            user.sendMessage("%prefix%&7User information for &f" + target.getPlayer().getName() + "&7:");
                            user.sendMessage("&7* Ping: &f" + target.ping());
                            user.sendMessage("&7* Flags: &f" + target.getChecks().stream().mapToDouble(check -> check.vl).count());
                            user.sendMessage("&7* Lunar Client: &f" + target.isUsingLunarClient());
                        } else user.sendMessage("%prefix%&7User not found!");
                    } else {
                        user.sendMessage("%prefix%&7Please specify a username to lookup!");
                    }
                }
            } else {
                user.sendMessage("&8&m----------------------------------------");
                user.sendMessage("                       " + HadesPlugin.getPrefix());
                user.sendMessage(" ");
                user.sendMessage(" &f/hades info <player> : " + "&7View information of a player");
                user.sendMessage(" &f/hades alerts : " + "&7Enable anti-cheat notifications!");
                user.sendMessage("&8&m----------------------------------------");
            }
            return true;
        }
        return false;
    }
}
