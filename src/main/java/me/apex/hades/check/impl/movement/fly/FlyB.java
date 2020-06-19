package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if(e instanceof FlyingEvent) {
            double diff = user.getDeltaY() - user.getLastDeltaY();
            if(diff == 0.0
                    && user.getAirTicks() > 6
                    && user.getPlayer().getVelocity().getY() < -0.075
                    && !user.isInLiquid()
                    && !user.isInWeb()
                    && !user.isOnClimbableBlock()
                    && !user.getPlayer().getAllowFlight()
                    && user.getPlayer().getVehicle() == null
                    && user.getTick() > 5
                    && elapsed(user.getTick(), user.getVelocityTick()) > 100) {
                flag(user, "consistent falling speed, d: " + diff);
            }
        }
    }
}
