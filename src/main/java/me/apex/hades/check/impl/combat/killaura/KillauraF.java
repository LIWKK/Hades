package me.apex.hades.check.impl.combat.killaura;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;
import org.bukkit.Bukkit;

import java.util.Deque;
import java.util.LinkedList;

public class KillauraF extends Check implements ClassInterface {
    public KillauraF(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    double lastYaw, lastDeltaXZ;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof AttackEvent) {
            double yaw = MathUtil.clamp180(Math.abs(user.getTo().getYaw() - user.getFrom().getYaw()));
            double yawDiff = Math.abs(yaw - lastYaw);

            double deltaXZ = user.getDeltaXZ();
            double deltaDiff = Math.abs(user.getDeltaXZ() - lastDeltaXZ);

            if (yawDiff >= 8.5 && deltaDiff > 0 && deltaDiff < 0.001) {
                if (++preVL > 1) {
                    flag(user, "Invalid Yaw With Movement");
                }
            }else preVL -= Math.min(preVL, 0.25);

            lastDeltaXZ = deltaXZ;
            lastYaw = yaw;
        }
    }
}
