package me.apex.hades.check;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.user.User;
import me.apex.hades.util.TaskUtil;
import me.apex.hades.util.text.ChatUtil;
import me.apex.hades.util.text.LogUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Check implements Listener {

    //Check Data
    public double preVL;
    public long lastFlag;
    public double vl, maxVL;
    public boolean enabled, punishable, dev;

    public Check() {
        Bukkit.getServer().getPluginManager().registerEvents(this, HadesPlugin.getInstance());

        try {
            enabled = HadesPlugin.getInstance().getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".enabled");
            punishable = HadesPlugin.getInstance().getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punishable");
            maxVL = HadesPlugin.getInstance().getConfig().getDouble("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".max-vl");
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

    public abstract void onHandle(PacketEvent e, User user);

    protected void flag(User user, String information) {
        assert user != null;
        vl++;
        lastFlag = time();
        TaskUtil.task(() -> {
            String formatColor = HadesPlugin.getInstance().getConfig().getString("lang.flag-format").replace("&", "§");
            TextComponent message = new TextComponent(formatColor.replace("%prefix%", HadesPlugin.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.getPlayer()));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+user.getPlayer().getName()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Info:\n§7* §c" + information + "\n§7Ping: §c" + user.ping() + "\n§7TPS: §c" + PacketEvents.getCurrentServerTPS() + "\n§7\n§7(Click to teleport)").create()));
            ChatUtil.informStaff(message);

            if (HadesPlugin.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
                LogUtils.logToFile(user.getLogFile(), HadesPlugin.getInstance().getConfig().getString("system.logging.log-format").replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information));
            if (HadesPlugin.getInstance().getConfig().getBoolean("system.logging.log-to-console")){
                Bukkit.getLogger().info("[Hades] " + HadesPlugin.getInstance().getConfig().getString("system.logging.log-format").replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(vl)).replace("%info%", information).replace("[%time%]", ""));
            }
            if (vl >= maxVL) {
                if (punishable) {
                    if (HadesPlugin.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
                        LogUtils.logToFile(user.getLogFile(), "%time% > [ACTION] " + HadesPlugin.getInstance().getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punish-command").replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.getInstance().getConfig().getString("lang.experimental-notation") : "")));
                    if (HadesPlugin.getInstance().getConfig().getBoolean("checks.broadcast-punishments"))
                        Bukkit.broadcastMessage(ChatUtil.color(HadesPlugin.getInstance().getConfig().getString("lang.broadcast-message").replace("%prefix%", HadesPlugin.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%newline%", "\n")));
                    TaskUtil.task(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), HadesPlugin.getInstance().getConfig().getString("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() +  ".punish-command").replace("%prefix%", HadesPlugin.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (dev ? HadesPlugin.getInstance().getConfig().getString("lang.experimental-notation") : ""))));
                }
            }
        });
    }

    //Time Util
    public long time() { return System.nanoTime() / 1000000; }
    public int elapsed(int now, int start) {
        return now - start;
    }
    public long elapsed(long now, long start) {
        return now - start;
    }
}
