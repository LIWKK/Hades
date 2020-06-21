package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "C")
public class SpeedC extends Check {
    double lastDistance;
    boolean lastGround;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (elapsed(user.getTick(), user.getLiquidTick()) <= 20 || user.getPlayer().getAllowFlight() || user.getPlayer().isInsideVehicle()) {
                return;
            }

            double deltaXZ = user.getDeltaXZ();

            boolean onGround = user.isOnGround(), lastOnGround = lastGround;
            double lastDist = lastDistance;
            double friction = 0.91f;
            double prediction = lastDist * friction + 0.025999999F;
            double diff = deltaXZ - prediction;

            if (diff > 1E-12 && !onGround && !lastOnGround && user.getLocation().getY() != user.getLastLocation().getY()) {
                if (++preVL > 2) {
                    flag(user, "Invalid Predicted Friction: " + diff);
                }
            } else {
                preVL = Math.max(preVL - 0.125, 0);
            }

            lastDistance = deltaXZ;
            lastGround = onGround;
        }
    }
}
