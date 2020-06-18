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
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.deltaY >= 0.0
                    && !PlayerUtil.isOnGround(user.player)
                    && user.airTicks > 9
                    && !user.isInLiquid()
                    && !user.isInWeb()
                    && !user.isOnClimbableBlock()
                    && !user.player.getAllowFlight()
                    && user.player.getVehicle() == null
                    && user.tick > 5) {
                if (++preVL > 3)
                    flag(user, "y motion higher than 0, m: " + user.deltaY);
            } else preVL *= 0.75;
        }
    }

}