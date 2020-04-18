package me.apex.hades.utils.command.impl;

import me.apex.hades.Hades;
import me.apex.hades.check.Check;
import me.apex.hades.utils.command.Command;
import me.apex.hades.data.User;
import me.apex.hades.event.EventHandler;
import me.apex.hades.utils.gui.impl.Homepage;
import me.apex.hades.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AnticheatCommand extends Command {

    public AnticheatCommand() {
        super(Hades.instance.getConfig().getString("base-command"));
    }

    @Override
    public void onCommand(User user, String[] args) {
        String base = Hades.instance.getConfig().getString("base-command");
        if(!user.getPlayer().hasPermission(base + ".command"))
        {
            user.sendMessage(Hades.instance.getConfig().getString("no-permission"));
            return;
        }
        if(args.length == 0)
        {
            sendCommandList(user, base);
        }else {
            if(args[0].equalsIgnoreCase("gui"))
            {
                new Homepage().display(user.getPlayer());
            }else if(args[0].equalsIgnoreCase("alerts"))
            {
                if(!user.data.alerts)
                {
                    user.data.alerts = true;
                    user.sendMessage(Hades.instance.prefix + "Enabled anticheat alerts!");
                }else
                {
                    user.data.alerts = false;
                    user.sendMessage(Hades.instance.prefix + "Disabled anticheat alerts!");
                }
            }else if(args[0].equalsIgnoreCase("info"))
            {
                if(args.length < 2)
                {
                    user.sendMessage("&cUsage: &7/" + base + " info <player>");
                    return;
                }

                Player p = Bukkit.getPlayer(args[1]);
                if(p == null)
                {
                    user.sendMessage("&cPlayer is not online!");
                    return;
                }
                User target = UserManager.INSTANCE.getUser(p.getUniqueId());

                String suffix = Hades.prefix.split(" ")[1];
                user.sendMessage("&7-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                user.sendMessage("&8User: " + suffix + p.getName());
                user.sendMessage("&8Ping: " + suffix + target.data.ping);
                user.sendMessage("&8Address: " + suffix + target.data.ip.split(":")[0].replace("/", ""));
                user.sendMessage("&7-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            }else if(args[0].equalsIgnoreCase("debug"))
            {
                if(args.length < 2)
                {
                    user.sendMessage("&cUsage: &7/" + base + " debug <check> <check-type>");
                    return;
                }

                if(args.length == 2)
                {
                    for(EventHandler handler : user.handlers)
                    {
                        if(handler instanceof Check)
                        {
                            Check check = (Check) handler;
                            if(check.name.split(" ")[0].equalsIgnoreCase(args[1]))
                            {
                                if(!user.debuggingChecks.contains(check))
                                {
                                    user.debuggingChecks.add(check);
                                    user.sendMessage("&7Now debugging &6" + check.name + "&7.");
                                }else
                                {
                                    user.debuggingChecks.remove(check);
                                    user.sendMessage("&7Stopped debugging &6" + check.name + "&7.");
                                }
                                return;
                            }
                        }
                    }
                    user.sendMessage("&cCheck not found!");
                }else if(args.length > 2)
                {
                    for(EventHandler handler : user.handlers)
                    {
                        if(handler instanceof Check)
                        {
                            Check check = (Check) handler;
                            if(check.name.split(" ")[0].equalsIgnoreCase(args[1]))
                            {
                                if(check.name.split(" ")[1].replace("(", "").replace(")", "").equalsIgnoreCase(args[2]))
                                {
                                    if(!user.debuggingChecks.contains(check))
                                    {
                                        user.debuggingChecks.add(check);
                                        user.sendMessage("&7Now debugging &6" + check.name + "&7.");
                                    }else
                                    {
                                        user.debuggingChecks.remove(check);
                                        user.sendMessage("&7Stopped debugging &6" + check.name + "&7.");
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    user.sendMessage("&cCheck not found!");
                }
            }else{
                sendCommandList(user, base);
            }
        }
    }

    private void sendCommandList(User user, String base)
    {
        user.sendMessage(Hades.prefix);
        user.sendMessage("&8* &7/" + base + " gui &8- &7Open anticheat panel.");
        user.sendMessage("&8* &7/" + base + " info &8- &7View player information.");
        user.sendMessage("&8* &7/" + base + " alerts &8- &7Toggle anticheat alerts.");
        user.sendMessage("&8* &7/" + base + " debug &8- &7Debug anticheat checks.");
    }
}
