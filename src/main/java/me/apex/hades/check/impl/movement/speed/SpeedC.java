package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "C")
public class SpeedC extends Check {
    boolean lastGround;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (elapsed(user.getTick(), user.getLiquidTick()) <= 20 || elapsed(user.getTick(), user.getTeleportTick()) < 20 || elapsed(user.getTick(), user.getFlyingTick()) < 40 || user.getPlayer().isInsideVehicle() || elapsed(user.getTick(), user.getVelocityTick()) <= 20 || user.getPlayer().isFlying()) {
                return;
            }

            boolean onGround = user.isOnGround(), lastOnGround = lastGround;
            double friction = 0.91F;
            double prediction = user.getLastDeltaXZ() * friction + 0.025999999F;
            double diff = user.getDeltaXZ() - prediction;

            if (diff > 1E-12 && !onGround && !lastOnGround && user.getLocation().getY() != user.getLastLocation().getY()) {
                if (++preVL > 6) {
                    flag(user, "invalid predicted dist, d: " + diff);
                }
            } else {
                preVL = Math.max(preVL - 0.125, 0);
            }

            lastGround = onGround;
        }
    }
}
