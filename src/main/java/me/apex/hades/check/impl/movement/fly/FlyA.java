package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Fly", type = "A")
public class FlyA extends Check {
    private double lastGround;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.isOnGround()) {
                lastGround = user.getLocation().getY();
            } else {
                if (user.getTeleportTick() > 0 || user.getPlayer().getAllowFlight() || user.isOnClimbableBlock() || user.isInWeb() || user.isInLiquid() || user.isInLiquid()) {
                    preVL = 0;
                    return;
                }

                double dist = user.getLocation().getY() - lastGround;
                double velocity = user.getPlayer().getVelocity().getY();

                if (dist >= 1.3 && user.getLocation().getY() >= user.getLastLocation().getY() && velocity < -0.06D && user.getPlayer().getVehicle() == null) {
                    if (preVL++ > 9) {
                        flag(user, "curY = " + user.getLocation().getY() + ", lastGround = " + lastGround);
                    }
                } else preVL = 0;
            }
        }
    }
}
