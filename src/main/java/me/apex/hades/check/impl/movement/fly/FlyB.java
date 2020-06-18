package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.location.CustomLocation;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {
    double lastY;
    boolean lastGround;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.isWaitingForMovementVerify() || !user.isSafe() || user.getMountedTicks() > 0 || user.isSwitchedGamemodes() || user.getBlockData().liquidTicks > 0 || user.blockData.climbableTicks > 0 || TimeUtils.elapsed(user.lastFullTeleport) < 1000L || user.getPlayer().getAllowFlight()) {
                return;
            }
            CustomLocation to = user.getTo(), from = user.getFrom();
            double distY = to.getY() - from.getY(), predictedDist = (lastY - 0.08D) * 0.9800000190734863D;

            boolean serverOnGround = user.isOnGround(), serverLastGround = lastGround;
            boolean clientOnGround = user.isClientGround(), clientLastGround = user.isLastClientGround();

            if (Math.abs(predictedDist) <= 0.005D) {
                predictedDist = 0;
            }
            if (user.blockData.blockAboveTicks > 0) { //fixes false even tho is hardcodes, but better then being bypassed
                if (Math.abs(predictedDist) == 0.08307781780646571 || Math.abs(predictedDist) == 0.04518702986887144 || distY <= 0.3) {
                    return;
                }
            }
            if ((!clientOnGround && !clientLastGround)) {
                if (Math.abs(distY - predictedDist) > 1E-12) {
                    if (++preVL > 2) {
                        flag(user, "" + Math.abs(distY - predictedDist) + " " + Math.abs(predictedDist));
                    }
                }else preVL -= Math.min(preVL, 0.5);
            }
            lastGround = serverOnGround;
            lastY = distY;
        }
    }
}
