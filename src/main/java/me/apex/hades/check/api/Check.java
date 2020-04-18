package me.apex.hades.check.api;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import lombok.Getter;
import lombok.Setter;
import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.listeners.event.HadesFlagEvent;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.LogUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
@Setter
public abstract class Check {

    //Info
    private List<Violation> violations;

    private boolean enabled, punishable, dev;

    public double vl, maxViolations;

    private final Executor executor;

    private long lastFlag;

    public Check() {
        violations = new ArrayList();

        enabled = Hades.getInstance().getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".enabled");
        punishable = Hades.getInstance().getConfig().getBoolean("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".punishable");
        maxViolations = Hades.getInstance().getConfig().getDouble("checks.detections." + getName().toLowerCase() + "." + getType().toLowerCase() + ".max-vl");
        this.executor = Executors.newFixedThreadPool(5);
    }

    protected void flag(User user, String information) {
        if (user == null || user.getPlayer() == null) return;
        lastFlag = System.currentTimeMillis();
        violations.add(new Violation(information));
        this.executor.execute(() -> {
            ChatUtils.informStaff(Hades.getInstance().getConfig().getString("lang.flag-format").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (isDev() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(getViolations().size())).replace("%info%", information), getViolations().size());
            if (Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
                LogUtils.logToFile(user.getLogFile(), Hades.getInstance().getConfig().getString("system.logging.log-format").replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (isDev() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(getViolations().size())).replace("%info%", information));

            if (getViolations().size() >= getMaxViolations()) {
                if (isPunishable() && !user.banned) {
                    user.banned = true;
                    if (Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
                        LogUtils.logToFile(user.getLogFile(), "%time% > [ACTION] " + Hades.getInstance().getConfig().getString("checks.punish-command").replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (isDev() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")));
                    if (Hades.getInstance().getConfig().getBoolean("checks.broadcast-punishments"))
                        Bukkit.broadcastMessage(ChatUtils.color(Hades.getInstance().getConfig().getString("lang.broadcast-message").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%newline%", "\n")));
                    TaskUtils.run(() -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Hades.getInstance().getConfig().getString("checks.punish-command").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", getName()).replace("%checktype%", getType() + (isDev() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")));
                    });
                }
            }
            Bukkit.getPluginManager().callEvent(new HadesFlagEvent(user.getPlayer(), this, information));
        });
    }

    public abstract void onPacket(PacketReceiveEvent e, User user);

    //Get Check Info
    public String getName() {
        return CheckManager.INSTANCE.getCheckInfo(this).name();
    }

    public String getType() {
        return CheckManager.INSTANCE.getCheckInfo(this).type();
    }

    //Flag Util
    public void reset(long afterDelay) {
        if(System.currentTimeMillis() - lastFlag >= afterDelay)
            vl = 0;
    }
}
