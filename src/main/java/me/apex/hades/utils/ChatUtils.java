public class ChatUtils {

    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void informStaff(TextComponent in, double vl) {
        UserManager.INSTANCE.getUsers().stream().filter(user -> user.isAlerts() && vl % (user.getFlagDelay() > 0 ? user.getFlagDelay() : 1) == 0).forEach(user -> user.getPlayer().spigot().sendMessage(in));
    }
}
