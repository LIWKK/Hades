package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "AutoClicker", type = "A")
public class AutoClickerA extends Check{
    int ticks;
    int cps;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (user.isBreakingOrPlacingBlock() || TimeUtils.elapsed(user.getBreakingOrPlacingTime()) < 1000L) {
                ticks = 0;
                cps = 0;
                return;
            }
            if (ticks == 20) {
                if (cps >= HadesPlugin.getInstance().getConfig().getInt("Max-CPS")) {
                    flag(user, "cps: " + cps);
                }
                cps = ticks = 0;
            }
        }else if (e instanceof SwingEvent) {
            cps++;
        }
    }
}
