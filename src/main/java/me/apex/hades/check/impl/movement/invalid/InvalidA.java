package me.apex.hades.check.impl.movement.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Invalid", type = "A")
public class InvalidA extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).hasMoved()) {
                if (user.deltaY == -user.lastDeltaY && user.deltaY != 0 && elapsed(user, user.teleportTick) > 0) {
                    if (++threshold > 1) {
                        flag(user, "repetitive vertical motions, m: " + user.deltaY);
                    }
                } else threshold = 0;
            }
        }
    }

}