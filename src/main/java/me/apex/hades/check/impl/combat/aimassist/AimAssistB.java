package me.apex.hades.check.impl.combat.aimassist;

import com.google.common.collect.Lists;
import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

import java.util.Collections;
import java.util.List;

public class AimAssistB extends Check implements ClassInterface {
    public AimAssistB(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
    double lastPitchDiff;
    double lastYawDiff;
    public List<Double> aimAssistPattern = Lists.newArrayList(), aimAssistPatternB = Lists.newArrayList();


    //AimAssist are not stable
    //But they will be kept for now
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            double pitchDifference = Math.abs(user.getTo().getPitch() - user.getFrom().getPitch());
            double yawDifference = MathUtil.clamp180(user.getTo().getYaw() - user.getFrom().getYaw());
            double pitchDelta = Math.abs(pitchDifference - lastPitchDiff);
            double yawDelta = Math.abs(yawDifference - lastYawDiff);
            if (yawDelta > 1.0 && yawDifference > 1.0 && pitchDelta > 0 && pitchDifference > 0) {
                aimAssistPattern.add(pitchDifference);
                aimAssistPatternB.add(pitchDelta);
                if (aimAssistPattern.size() > 10 && aimAssistPatternB.size() > 10) {
                    Collections.sort(aimAssistPattern);
                    Collections.sort(aimAssistPatternB);
                    double range = Math.abs(aimAssistPattern.get(aimAssistPattern.size() - 1) - aimAssistPattern.get(0));
                    double rangeB = Math.abs(aimAssistPatternB.get(aimAssistPatternB.size() - 1) - aimAssistPatternB.get(0));
                    double rangeDiff = Math.abs(range - rangeB);
                    if (rangeDiff < 0.01) {
                        flag(user, "Drip");
                    }

                    aimAssistPattern.clear();
                    aimAssistPatternB.clear();
                }
            }
            lastPitchDiff = pitchDifference;
            lastYawDiff = yawDifference;
        }
    }
}