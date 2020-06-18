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
    double lastPitchDiff;
    double lastYawDiff;

    //AimAssist are not stable
    //Keeping them for now
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            double pitchDifference = Math.abs(user.getTo().getPitch() - user.getFrom().getPitch());
            double yawDifference = MathUtil.clamp180(user.getTo().getYaw() - user.getFrom().getYaw());
            double pitchDelta = Math.abs(pitchDifference - lastPitchDiff);
            double yawDelta = Math.abs(yawDifference - lastYawDiff);
            if (yawDelta > 1.0 && yawDifference > 1.0 && pitchDifference < 0.01919 && pitchDelta < 0.01919 && pitchDelta > 0 && pitchDifference > 0) {
                flag(user, "Drip AimAssist ? "+pitchDifference);
            }
            lastPitchDiff = pitchDifference;
            lastYawDiff = yawDifference;
        }
    }
}
