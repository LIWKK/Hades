package me.apex.hades.check;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.user.User;
import me.apex.hades.util.ChatUtil;
import me.apex.hades.util.LogUtils;
import me.apex.hades.util.TaskUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public abstract class Check {

    //Check Data
    public double preVL;
    public long lastFlag;
    public double vl, maxVL;
    public boolean enabled, punishable, dev;

    public Check() {
        try {
            enabled = HadesPlugin.instance.getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".enabled");
            punishable = HadesPlugin.instance.getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punishable");
            maxVL = HadesPlugin.instance.getConfig().getDouble("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".max-vl");
        }catch (Exception e) { }

        //Call Post-Init
        init();
    }

    public String getName() {
        return CheckManager.getCheckInfo(this).name();
    }

    public String getType() {
        return CheckManager.getCheckInfo(this).type();
    }

    public void init() { }

    public abstract void onEvent(PacketEvent e, User user);

    protected void flag(User user, String information) {
        assert user != null;
        vl++;
        lastFlag = time();
        TaskUtil.run(() -> {
            String formatColor = HadesPlugin.instance.getConfig().getString("lang.flag-format").replace("&", "§");
            TextComponent message = new TextComponent(formatColor.replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.player));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.player.getName()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Info:\n§7* §c" + information + "\n§7Ping: §c" + user.ping() + "\n§7TPS: §c" + PacketEvents.getCurrentServerTPS() + "\n§7\n§7(Click to teleport)").create()));
            ChatUtil.informStaff(message);

            if (HadesPlugin.instance.getConfig().getBoolean("system.logging.file.enabled"))
                LogUtils.logToFile(user.logFile, HadesPlugin.instance.getConfig().getString("system.logging.log-format").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            if (HadesPlugin.instance.getConfig().getBoolean("system.logging.log-to-console")){
                Bukkit.getLogger().info("[Hades] " + HadesPlugin.instance.getConfig().getString("system.logging.log-format").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information).replace("[%time%]", ""));
            }
            if (vl >= maxVL) {
                if (punishable) {
                    if (HadesPlugin.instance.getConfig().getBoolean("system.logging.file.enabled"))
                        LogUtils.logToFile(user.logFile, "%time% > [ACTION] " + HadesPlugin.instance.getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punish-command").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")));
                    if (HadesPlugin.instance.getConfig().getBoolean("checks.broadcast-punishments"))
                        Bukkit.broadcastMessage(ChatUtil.color(HadesPlugin.instance.getConfig().getString("lang.broadcast-message").replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%newline%", "\n")));
                    TaskUtil.run(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), HadesPlugin.instance.getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() +  ".punish-command").replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : ""))));
                }
            }
        });
    }

    //Time Util
    public long time() { return System.nanoTime() / 1000000; }
    public int elapsed(int now, int start) {
        return now - start;
    }

}
