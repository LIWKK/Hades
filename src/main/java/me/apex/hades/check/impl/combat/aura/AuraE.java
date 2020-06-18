package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

@CheckInfo(name = "Aura", type = "E")
public class AuraE extends Check {

    private final Deque<Double> diffs = new LinkedList<>();
    private double average = 100;
    private float lastDeltaYaw;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof AttackEvent) {
            float deltaYaw = Math.abs(user.getLocation().getYaw() - user.getPreviousLocation().getYaw());
            float lastDeltaYaw = this.lastDeltaYaw;
            this.lastDeltaYaw = deltaYaw;

            double diff = Math.abs(deltaYaw - lastDeltaYaw);
            if (diff > 0.0) diffs.add(diff);

            if (diffs.size() >= 5) {
                double deviation = MathUtil.getStandardDeviation(diffs.stream().mapToDouble(d -> d).toArray());

                average = ((average * 19) + deviation) / 20;

                if (average < 5) {
                    if (++preVL > 2) {
                        flag(user, "low average deviation, a: " + average);
                    }
                } else preVL *= 0.75;

                diffs.clear();
            }
        }
    }
}
