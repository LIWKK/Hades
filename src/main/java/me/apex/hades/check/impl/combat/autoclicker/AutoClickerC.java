package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "AutoClicker", type = "C")
public class AutoClickerC extends Check {

    private long lastFlying;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof SwingEvent) {
            long timeDiff = Math.abs(e.getTimestamp() - lastFlying);

            if (timeDiff < 5) {
                if (vl++ > 10)
                    flag(user, "diff = " + timeDiff);
            } else vl = 0;
        } else if (e instanceof FlyingEvent) {
            lastFlying = e.getTimestamp();
        }
    }
}
