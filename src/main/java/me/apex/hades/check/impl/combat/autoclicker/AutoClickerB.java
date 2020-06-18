package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerB extends Check implements ClassInterface {
    public AutoClickerB(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    private final Deque<Long> ticks = new LinkedList<>();
    private double lastDeviation;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof SwingEvent) {
            if (!user.isBreakingOrPlacingBlock()) ticks.add((long) (user.getFlyingTick() * 50.0));
            if (ticks.size() >= 10) {
                double deviation = MathUtil.getStandardDeviation(ticks.stream().mapToLong(d -> d).toArray());
                double lastDeviation = this.lastDeviation;
                this.lastDeviation = deviation;

                double diff = Math.abs(deviation - lastDeviation);

                if (diff < 10) {
                    if (++preVL > 1) {
                        flag(user, "low deviation difference, d: " + diff);
                    }
                } else preVL = 0;

                ticks.clear();
            }
        }
    }
}
