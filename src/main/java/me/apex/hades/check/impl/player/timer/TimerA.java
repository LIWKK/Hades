package me.apex.hades.check.impl.player.timer;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

public class TimerA extends Check implements ClassInterface {
    public TimerA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    private final Deque<Long> flyingDeque = new LinkedList<>();
    private double lastDeviation;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            flyingDeque.add(System.currentTimeMillis());

            if(flyingDeque.size() == 50) {
                double deviation = MathUtil.getStandardDeviation(flyingDeque.stream().mapToLong(l -> l).toArray());
                double lastDeviation = this.lastDeviation;
                this.lastDeviation = deviation;

                if(deviation <= 710 && (Math.abs(deviation - lastDeviation) < 20)) {
                    if(preVL++ > 1) {
                        flag(user, "high packet rate, d: " + deviation);
                    }
                }else preVL = 0;
            }
        }
    }
}
