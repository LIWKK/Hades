package me.apex.hades.check;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.ChatUtil;
import org.bukkit.Bukkit;

public abstract class Check {

    //Check Data
    public int vl, maxVL;
    public double threshold;
    public boolean enabled, dev;

    public Check() {
        vl = 0;
        maxVL = 10;

        //Call Post-Init
        init();
    }

    //Get Check Info
    public static String getName(Check check) {
        return check.getClass().getAnnotation(CheckInfo.class).name();
    }

    public static String getType(Check check) {
        return check.getClass().getAnnotation(CheckInfo.class).type();
    }

    public abstract void init();

    public abstract void onEvent(PacketEvent e, User user);

    protected void flag(User user, String information) {
        assert user != null;
        vl++;
        //Change alerts to old ones!
        ChatUtil.informStaff("&8[&7Hades&8] &f" + user.player.getName() + " &7has flagged &f" + getName(this) + " &8(&f" + getType(this) + (dev ? "&7*" : "") + "&8) &7VL:" + vl);
        //No Auto ban, just alert ban
        if (vl >= maxVL) {
            vl = 0;
            //Change alerts to old ones!
            Bukkit.broadcastMessage(ChatUtil.color("&7User &f" + user.player.getName() + " &7was automatically banned by &fHades &7for cheating."));
        }
    }

    //Time Util
    public long time() { return System.nanoTime() / 1000000; }
    public int elapsed(int now, int start) {
        return now - start;
    }

}
