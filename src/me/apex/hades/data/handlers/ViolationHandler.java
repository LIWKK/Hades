package me.apex.hades.data.handlers;

import me.apex.hades.Hades;
import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.managers.UserManager;
import me.apex.hades.event.Event;
import me.apex.hades.event.EventHandler;
import me.apex.hades.event.impl.UserViolationEvent;
import me.apex.hades.managers.LogManager;
import me.apex.hades.utils.ChatUtils;
import org.bukkit.Bukkit;

public class ViolationHandler extends EventHandler {

    public ViolationHandler(User user) {
        super(user);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof UserViolationEvent) {
            UserViolationEvent e = (UserViolationEvent) event;
            for (User user : UserManager.INSTANCE.users) {
                if (user.data.alerts) {
                    StringBuilder stringBuilder = new StringBuilder(e.getCheck().name); // better on performance

                    if (e.getCheck().experimental) stringBuilder.append(ChatUtils.color(" [Exp]")); // Better on performance

                    user.sendMessage(Hades.instance.getConfig().getString("flag-format")
                            .replace("%player%", e.getCheck().user.getPlayer().getName())
                            .replace("%check%", stringBuilder.toString())
                            .replace("%vl%", String.valueOf(e.getCheck().violations.size()))
                            .replace("%ping%", String.valueOf(e.getCheck().user.data.ping))
                            .replace("%info%", e.getInformation()));
                }
            }

            //Log Alert
            if (Hades.instance.getConfig().getBoolean("log-violations"))
                LogManager.INSTANCE.addLog(e.getCheck().user, e.getCheck().name + " INFO: (VL: " + e.getCheck().violations.size() + ", Ping: " + e.getCheck().user.data.ping + ")");

            if (e.getCheck().violations.size() == e.getCheck().maxViolations && !e.getCheck().experimental) {
                LogManager.INSTANCE.addLog(e.getCheck().user, "[BAN] " + e.getCheck().name);
                e.getCheck().user.punish(Hades.instance.getConfig().getString("punish-command").replace("%player%", e.getCheck().user.getPlayer().getName()).replace("%newline%", "\n"));
                if (Hades.instance.getConfig().getBoolean("broadcast-bans"))
                    Bukkit.broadcastMessage(ChatUtils.color(Hades.instance.getConfig().getString("ban-broadcast").replace("%player%", e.getCheck().user.getPlayer().getName()).replace("%cheat%", e.getCheck().name.split(" ")[0]).replace("%newline%", "\n")));
            }
        }
    }
}
