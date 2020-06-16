package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;

@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            double diff = user.deltaY - user.lastDeltaY;
            if (diff >= 0.0
                    && !PlayerUtil.isOnGround(user.player)
                    && user.player.getVelocity().getY() < 0
                    && !PlayerUtil.isInLiquid(user.player)
                    && !PlayerUtil.isInWeb(user.player)
                    && !user.player.getAllowFlight()
                    && user.player.getVehicle() == null) {
                if (++preVL > 3)
                    flag(user, "y motion higher than 0, m: " + diff);
            } else preVL *= 0.75;
        }
    }

}