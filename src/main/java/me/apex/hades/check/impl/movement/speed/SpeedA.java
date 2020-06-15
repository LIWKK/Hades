package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "A")
public class SpeedA extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).hasMoved()) {
                double diff = user.deltaXZ - user.lastDeltaXZ;
                if (diff == 0.0 && user.deltaXZ > 0.29
                        && !user.player.getAllowFlight()) {
                    flag(user, "consistent speed, diff: " + diff);
                }
            }
        }
    }

}
