package me.apex.hades.utils;

import me.apex.hades.objects.UserManager;
import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void informStaff(String in, double vl) {
        UserManager.INSTANCE.getUsers().stream().filter(user -> user.isAlerts() && vl % (user.getFlagDelay() > 0 ? user.getFlagDelay() : 1) == 0).forEach(user -> user.getPlayer().sendMessage(color(in)));
    }

}
