package me.apex.hades.command.impl;

import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.command.api.CommandAdapter;
import me.apex.hades.command.api.UserInput;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.menu.impl.HomeMenu;
import me.apex.hades.utils.HastebinUtils;
import me.apex.hades.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HadesCommand extends CommandAdapter {

    @Override
    public boolean onCommand(User user, UserInput input) {
        String color = Hades.getInstance().getConfig().getString("lang.base-message-color"), cmd = Hades.getInstance().baseCommand.replaceFirst(String.valueOf(Hades.getInstance().baseCommand.charAt(0)), String.valueOf(Hades.getInstance().baseCommand.charAt(0)).toUpperCase());
        if (input.label().equalsIgnoreCase("alerts")) {
            if (user.getPlayer().hasPermission(Hades.getInstance().basePermission + ".alerts")) {
                if (user.isAlerts()) {
                    user.setAlerts(false);
                    user.sendMessage(Hades.getInstance().getConfig().getString("lang.base-message-color") + "You are no longer viewing anticheat alerts.");
                } else {
                    user.setAlerts(true);
                    user.sendMessage(Hades.getInstance().getConfig().getString("lang.base-message-color") + "You are now viewing anticheat alerts.");
                }
            } else user.sendMessage(Hades.getInstance().getConfig().getString("lang.no-permission"));
            return true;
        } else if (input.label().equalsIgnoreCase(Hades.getInstance().baseCommand)) {
            if (user.getPlayer().hasPermission(Hades.getInstance().basePermission + "." + (input.args().length > 0 ? input.args()[0].toLowerCase() : "command"))) {
                if (input.args().length > 0) {
                    if (input.args()[0].equalsIgnoreCase("gui")) {
                        new HomeMenu().show(user.getPlayer());
                    } else if (input.args()[0].equalsIgnoreCase("info")) {
                        if (input.args().length > 1) {
                            Player target = Bukkit.getPlayer(input.args()[1]);
                            if (target != null) {
                                User t = UserManager.INSTANCE.getUser(target.getUniqueId());
                                int totalViolations = 0;
                                for (Check check : t.getChecks()) totalViolations += check.violations.size();
                                user.sendMessage("&8&m----------------------------------------");
                                user.sendMessage(color + "&l" + target.getName() + "'s Information");
                                user.sendMessage(" ");
                                user.sendMessage(color + "Total Violations: &f" + totalViolations);
                                user.sendMessage(color + "Brand: &fvanilla");
                                user.sendMessage(color + "Ping: &f" + t.getPing());
                                user.sendMessage(color + "Lagging: &f" + user.isLagging());
                                user.sendMessage(color + "IP Address: &f" + user.getAddress());
                                user.sendMessage("&8&m----------------------------------------");
                            } else user.sendMessage(color + "Player is not online or does not exist!");
                        } else user.sendMessage("&7Usage: " + color + "/" + cmd + " info (player)");
                    } else if (input.args()[0].equalsIgnoreCase("reload")) {
                        long start = System.currentTimeMillis();
                        if (Hades.getInstance().reloadPlugin()) {
                            user.sendMessage(color + "Successfully reloaded anticheat! (" + Math.abs(start - System.currentTimeMillis()) + "ms)");
                        } else
                            user.sendMessage(color + "Failed to reloaded anticheat!");
                    }
                } else {
                    user.sendMessage("&8&m----------------------------------------");
                    user.sendMessage(color + "&lHelp for Command /" + cmd + ":");
                    user.sendMessage(" ");
                    user.sendMessage(color + "/" + cmd + " &fgui : " + "&7View control panel");
                    user.sendMessage(color + "/" + cmd + " &finfo <player> : " + "&7View information of a player");
                    user.sendMessage(color + "/" + cmd + " &freload : " + "&7Reload entire anticheat and configuration");
                    user.sendMessage("&8&m----------------------------------------");
                }
            } else user.sendMessage(Hades.getInstance().getConfig().getString("lang.no-permission"));
            return true;
        } else if (input.label().equalsIgnoreCase("logs")) {
            if (user.getPlayer().hasPermission(Hades.getInstance().basePermission + ".logs")) {
                if (input.args().length > 0) {
                    user.sendMessage("&7Attempting to upload logs to Hastebin...");
                    if (Bukkit.getOfflinePlayer(input.args()[0]) == null) {
                        user.sendMessage(color + "&cNo logs found for " + input.args()[0] + ".");
                        return true;
                    }
                    UUID targetUUID = Bukkit.getOfflinePlayer(input.args()[0]).getUniqueId();
                    StringBuilder end = new StringBuilder("AntiCheat Logs for " + Bukkit.getOfflinePlayer(input.args()[0]).getName());
                    LogUtils.TextFile logFile = new LogUtils.TextFile("" + targetUUID, Hades.getInstance().getConfig().getString("system.logging.file.path"));
                    logFile.readTextFile();
                    if (logFile.getLines().size() <= 0) {
                        user.sendMessage(color + "No logs found for " + input.args()[0] + ".");
                        return true;
                    }
                    for (String s : logFile.getLines()) {
                        end.append("\n").append(s);
                    }

                    String url = HastebinUtils.uploadPaste(end.toString());
                    user.sendMessage(color + "Uploaded logs at: &7" + url);
                } else user.sendMessage("&7Usage: " + color + "/logs (player)");
            } else user.sendMessage(Hades.getInstance().getConfig().getString("lang.no-permission"));
            return true;
        } else return false;
    }
}
