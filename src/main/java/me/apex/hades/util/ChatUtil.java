package me.apex.hades.util;

import me.apex.hades.user.UserManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public class ChatUtil {

    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void informStaff(TextComponent in) {
        UserManager.users.stream().filter(user -> user.alerts).forEach(user -> user.player.spigot().sendMessage(in));
    }

}
