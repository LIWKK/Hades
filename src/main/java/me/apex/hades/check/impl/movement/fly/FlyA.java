package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Fly", type = "A")
public class FlyA extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.getDeltaY() >= 0.0
                    && user.getAirTicks() > 6
                    && user.getPlayer().getVelocity().getY() < -0.075
                    && !user.isInLiquid()
                    && !user.isInWeb()
                    && !user.isOnClimbableBlock()
                    && !user.getPlayer().getAllowFlight()
                    && user.getPlayer().getVehicle() == null
                    && user.getTick() > 5) {
                if (++preVL > 2)
                    flag(user, "y motion higher than 0, m: " + user.getDeltaY());
            }else preVL = 0;
        }
    }

}