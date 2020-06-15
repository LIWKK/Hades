package me.apex.hades.util;

import me.apex.hades.user.UserManager;
import org.bukkit.ChatColor;

public class ChatUtil {

    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void informStaff(String message) {
        UserManager.users.stream().filter(user -> user.alerts).forEach(user -> user.player.sendMessage(color(message)));
    }

}
