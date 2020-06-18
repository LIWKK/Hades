package me.apex.hades.check.impl.combat.aimassist;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

public class AimAssistA extends Check implements ClassInterface {
    public AimAssistA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
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
