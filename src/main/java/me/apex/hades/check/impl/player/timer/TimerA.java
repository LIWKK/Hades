package me.apex.hades.check.impl.player.timer;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;
import me.apex.hades.utils.math.RollingAverageDouble;

import java.util.Deque;
import java.util.LinkedList;

public class TimerA extends Check implements ClassInterface {
    public TimerA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    long lastTimer, lastTimerMove;
    RollingAverageDouble timerRate = new RollingAverageDouble(40, 50.0);

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            long current = System.currentTimeMillis();
            long difference = current - lastTimerMove;

            timerRate.add((double) difference);

            if ((current - lastTimer) >= 1000L) {
                lastTimer = current;
                double timerSpeed = 50.0 / timerRate.getAverage();
                if (timerSpeed >= 1.01) {
                    if (++preVL > 4) {
                        flag(user, "Timer Speed: "+timerSpeed);
                    }
                }else preVL -= Math.min(preVL, 0.5);
            }
            lastTimerMove = current;
        }
    }
}
