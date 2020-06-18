package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.location.CustomLocation;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "Speed", type = "C")
public class SpeedC extends Check {

    double lastDistance;
    boolean lastGround;

    /*
            Prediction for player friction (really good check be thankful lol)

            Thanks - Tecnio
     */

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (user != null) {

                if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.getPlayer().getAllowFlight() || TimeUtils.secondsFromLong(user.getLastTeleport()) < 3L || user.getMountedTicks() > 0 || user.isSwitchedGamemodes() || user.getBlockData().stairTicks > 0 || user.getBlockData().slabTicks > 0) {
                    return;
                }

                CustomLocation from = user.getTo(), to = user.getFrom();

                double deltaXZ = Math.hypot(to.getX() - from.getX(), to.getZ() - from.getZ());

                boolean onGround = user.isOnGround(), lastOnGround = lastGround;
                double lastDist = lastDistance;
                double friction = 0.91f;
                double prediction = lastDist * friction + 0.025999999F;
                double diff = deltaXZ - prediction;

                if (diff > 1E-12 && !onGround && !lastOnGround && user.getTo().getY() != user.getFrom().getY()) {
                    if (++preVL > 1) {
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
}