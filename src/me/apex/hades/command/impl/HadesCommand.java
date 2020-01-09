package me.apex.hades.command.impl;

import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.command.api.CommandAdapter;
import me.apex.hades.command.api.UserInput;
import me.apex.hades.data.User;
import me.apex.hades.data.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HadesCommand extends CommandAdapter {

    @Override
    public boolean onCommand(User user, UserInput input) {
        String color = Hades.getInstance().getConfig().getString("lang.base-message-color"), cmd = Hades.getInstance().baseCommand.replaceFirst(String.valueOf(Hades.getInstance().baseCommand.charAt(0)), String.valueOf(Hades.getInstance().baseCommand.charAt(0)).toUpperCase());
        if(input.label().equalsIgnoreCase("alerts"))
        {
            if(user.getPlayer().hasPermission(Hades.getInstance().basePermission + ".alerts"))
            {
                if(user.isAlerts())
                {
                    user.setAlerts(false);
                    user.sendMessage(Hades.getInstance().getConfig().getString("lang.base-message-color") + "You are no longer viewing anticheat alerts.");
                }else
                {
                    user.setAlerts(true);
                    user.sendMessage(Hades.getInstance().getConfig().getString("lang.base-message-color") + "You are now viewing anticheat alerts.");
                }
            }else user.sendMessage(Hades.getInstance().getConfig().getString("lang.no-permission"));
            return true;
        }else if(input.label().equalsIgnoreCase(Hades.getInstance().baseCommand))
        {
            if(user.getPlayer().hasPermission(Hades.getInstance().basePermission + "." + (input.args().length > 0 ? input.args()[0].toLowerCase() : "command")))
            {
                if(input.args().length > 0)
                {
                    if(input.args()[0].equalsIgnoreCase("info"))
                    {
                        if(input.args().length > 1)
                        {
                            Player target = Bukkit.getPlayer(input.args()[1]);
                            if(target != null)
                            {
                                User t = UserManager.INSTANCE.getUser(target.getUniqueId());
                                int totalViolations = 0;
                                for(Check check : t.getChecks()) totalViolations += check.getViolations().size();
                                user.sendMessage(color + target.getName() + "'s Information:");
                                user.sendMessage("&7- " + color + "Total Violations: &f" + totalViolations);
                                user.sendMessage("&7- " + color + "Version: &f1.8");
                                user.sendMessage("&7- " + color + "Brand: &fvanilla");
                                user.sendMessage("&7- " + color + "Ping: &f" + t.getPing());
                                user.sendMessage("&7- " + color + "Lagging: &f" + user.isLagging());
                            }else user.sendMessage(color + "Player is not online or does not exist!");
                        }else user.sendMessage("&7Usage: " + color + "/" + cmd + " info (player)");
                    }else if(input.args()[0].equalsIgnoreCase("reload"))
                    {
                        if(Hades.getInstance().reloadPlugin())
                            user.sendMessage(color + "Successfully reloaded anticheat!");
                        else
                            user.sendMessage(color + "Failed to reloaded anticheat!");
                    }
                }else
                {
                    user.sendMessage(color + "Help for Command /" + cmd + ":");
                    user.sendMessage("&7- " + color + "/" + cmd + " gui : &fView control panel");
                    user.sendMessage("&7- " + color + "/" + cmd + " info (player) : &fView information of a player");
                    user.sendMessage("&7- " + color + "/" + cmd + " reload : &fReload entire anticheat and configuration");
                }
            }
            return true;
        }else
        {
            return false;
        }
    }

}
