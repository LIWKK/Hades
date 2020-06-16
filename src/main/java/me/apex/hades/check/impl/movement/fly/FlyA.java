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
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.onGround()) {
                lastGround = user.location.getY();
            } else {
                if (user.teleportTick > 0 || user.player.getAllowFlight() || user.isOnClimbableBlock() || user.isInWeb() || user.isInLiquidReflection() || user.isInLiquid()) {
                    vl = 0;
                    return;
                }

                double dist = user.location.getY() - lastGround;
                double velocity = user.player.getVelocity().getY();

                if (dist >= 1.3 && user.location.getY() >= user.lastLocation.getY() && velocity < -0.06D && user.player.getVehicle() == null) {
                    if (vl++ > 9){
                        flag(user, "curY = " + user.location.getY() + ", lastGround = " + lastGround);
                    }
                } else vl = 0;
            }
        }
    }

}
