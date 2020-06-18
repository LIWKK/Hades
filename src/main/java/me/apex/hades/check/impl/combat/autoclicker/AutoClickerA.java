package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;

public class AutoClickerA extends Check implements ClassInterface {
    public AutoClickerA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    int ticks;
    int cps;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingEvent) {
            if (user.isBreakingOrPlacingBlock() || TimeUtils.elapsed(user.getBreakingOrPlacingTime()) < 1000L) {
                ticks = 0;
                cps = 0;
                return;
            }
            if (ticks == 20) {
                if (cps >= 22) {
                    flag(user, "Cps: "+cps);
                }
                cps = ticks = 0;
            }
        }else if (e instanceof SwingEvent) {
            cps++;
        }
    }
}
