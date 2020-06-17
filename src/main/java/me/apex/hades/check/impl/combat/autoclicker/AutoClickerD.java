package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;

@CheckInfo(name = "AutoClicker", type = "D")
public class AutoClickerD extends Check {

    private int ticks, lastTicks;
    private double lastDelay;
    boolean digging;

    // Disabled due to major falsing and no use
    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof SwingEvent) {
//            double delay = Math.abs(ticks - lastTicks);
//
//            if (delay != 0 || lastDelay != 0) {
//                double lcd = MathUtil.lcd((long) delay, (long) lastDelay);
//                double fixedLcd = lcd * Math.PI;
//                double remainder = Math.IEEEremainder(lcd, lastDelay) / Math.PI;
//
//                if (!user.digging) {
//                    if (Double.isNaN(remainder)) {
//                        if (preVL++ > 2.5) {
//                            flag(user, "remainder = " + remainder);
//                        }
//                    } else preVL -= preVL > 0 ? 0.5 : 0;
//                }
//            } else preVL *= 0.75;
//
//            this.lastTicks = ticks;
//            this.ticks = 0;
        } else if (e instanceof FlyingEvent) {
//            ticks++;
        }
    }
}
