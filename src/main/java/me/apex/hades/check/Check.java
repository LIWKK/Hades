package me.apex.hades.check;

import me.apex.hades.HadesPlugin;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.AnticheatListener;
import me.apex.hades.user.User;
import me.apex.hades.utils.other.ChatUtils;
import me.apex.hades.utils.other.LogUtils;
import me.apex.hades.utils.other.TaskUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public abstract class Check implements AnticheatListener{

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
        return CheckManager.INSTANCE.getCheckInfo(this).name();
    }

    public String getType() {
        return CheckManager.INSTANCE.getCheckInfo(this).type();
    }

    public void init() { }

    public abstract void onHandle(User user, AnticheatEvent e);


    protected void flag(User user, String information) {
        if(!enabled) return;
        assert user != null;
        vl++;
        lastFlag = time();
        TaskUtils.run(() -> {
            String formatColor = HadesPlugin.instance.getConfig().getString("lang.flag-format").replace("&", "§");
            TextComponent message = new TextComponent(formatColor.replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.player));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.player.getName()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Info:\n§7* §c" + information + "\n§7Ping: §c" + ((CraftPlayer)user.player).getHandle().ping).create()));
            if (user.alerts) user.player.spigot().sendMessage(message);
            ChatUtils.informStaff(message);

            if (HadesPlugin.instance.getConfig().getBoolean("system.logging.file.enabled"))
                LogUtils.logToFile(user.getLogFile(), HadesPlugin.instance.getConfig().getString("system.logging.log-format").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            if (HadesPlugin.instance.getConfig().getBoolean("system.logging.log-to-console")){
                Bukkit.getLogger().info("[Hades] " + HadesPlugin.instance.getConfig().getString("system.logging.log-format").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information).replace("[%time%]", ""));
            }
            if (vl >= maxVL) {
                if (punishable) {
                    if (HadesPlugin.instance.getConfig().getBoolean("system.logging.file.enabled"))
                        LogUtils.logToFile(user.getLogFile(), "%time% > [ACTION] " + HadesPlugin.instance.getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punish-command").replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : "")));
                    if (HadesPlugin.instance.getConfig().getBoolean("checks.broadcast-punishments"))
                        Bukkit.broadcastMessage(ChatUtils.color(HadesPlugin.instance.getConfig().getString("lang.broadcast-message").replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%newline%", "\n")));
                    TaskUtils.run(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), HadesPlugin.instance.getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() +  ".punish-command").replace("%prefix%", HadesPlugin.instance.getPrefix()).replace("%player%", user.player.getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.instance.getConfig().getString("lang.experimental-notation") : ""))));
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