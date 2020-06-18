package me.apex.hades.check;

import com.mysql.jdbc.TimeUtil;
import me.apex.hades.HadesPlugin;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.AnticheatListener;
import me.apex.hades.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public class Check implements AnticheatListener, ClassInterface {
    private String checkName, letter;
    private Type type;
    private boolean enabled;
    public double preVL;

    public Check(String checkName, String letter, Type type, boolean enabled) {
        this.checkName = checkName;
        this.letter = letter;
        this.type = type;
        this.enabled = enabled;
    }

    public void flag(User user, String... string) {

        user.setViolation(user.getViolation() + 1);

        StringBuilder dataStr = new StringBuilder();

        if (string.length > 0) {
            for (String s : string) {
                dataStr.append(s).append((string.length == 1 ? "" : ", "));
            }
        }

        String alert = HadesPlugin.getInstance().getConfigManager().getAlertMessage().replace("%CHECK%", checkName).replace("%TYPE%", letter).replace("%VL%", String.valueOf(user.getViolation())).replace("%PLAYER%", user.getPlayer().getName());
        TextComponent textComponent = new TextComponent(alert);
        if (dataStr.length() > 0) {
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + dataStr.toString()).create()));
        }

        HadesPlugin.userManager.getUsers().stream().filter(staff -> (staff.getPlayer().isOp() || staff.getPlayer().hasPermission("anticheat.alerts"))).forEach(staff -> staff.getPlayer().spigot().sendMessage(textComponent));

    }

    @Override
    public void onHandle(User user, AnticheatEvent e) {

    }
}
