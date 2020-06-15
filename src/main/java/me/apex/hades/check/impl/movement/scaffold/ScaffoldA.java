package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.PlaceEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Scaffold", type = "A")
public class ScaffoldA extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    private long lastFlying;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof PlaceEvent) {
            long timeDiff = time() - lastFlying;

            if (timeDiff < 5) {
                if (++threshold > 10) {
                    flag(user, "low flying delay, d: " + timeDiff);
                }
            } else threshold = 0;
        } else if (e instanceof FlyingEvent) {
            lastFlying = time();
        }
    }

}
