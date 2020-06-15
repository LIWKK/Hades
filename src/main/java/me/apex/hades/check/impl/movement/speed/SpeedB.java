package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "B")
public class SpeedB extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).hasMoved()) {
                double max = 0.34;
                max *= user.player.getWalkSpeed() / 0.2;

                if (elapsed(user, user.iceTick) < 20 || elapsed(user, user.slimeTick) < 20) max += 0.34;
                if (elapsed(user, user.underBlockTick) < 40) max += 0.91;
                if (elapsed(user, user.velocityTick) < 20) max += 0.21;

                if (user.deltaXZ > max
                        && elapsed(user, user.teleportTick) > 20
                        && !user.player.getAllowFlight()) {
                    if (++threshold > 6) {
                        flag(user, "breached limit, s: " + user.deltaXZ);
                    }
                } else threshold *= 0.75;
            }
        }
    }

}
