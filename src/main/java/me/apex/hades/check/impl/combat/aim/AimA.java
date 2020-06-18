package me.apex.hades.check.impl.combat.aim;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

@CheckInfo(name = "Aim", type = "A")
public class AimA extends Check {
    double lastYawDiff;


    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            double pitchDifference = Math.abs(user.getTo().getPitch() - user.getFrom().getPitch());
            double yawDifference = MathUtil.clamp180(user.getTo().getYaw() - user.getFrom().getYaw());
            double yawDelta = Math.abs(yawDifference - lastYawDiff);
            if (pitchDifference < 0.00001 && pitchDifference > 0 && yawDifference > 2 && yawDelta > 2) {
                flag(user, "AimAssist: "+pitchDifference);
            }
            lastYawDiff = yawDifference;
        }
    }
}
