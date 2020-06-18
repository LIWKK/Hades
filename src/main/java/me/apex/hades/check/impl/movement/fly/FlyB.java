package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.location.CustomLocation;
import me.apex.hades.utils.time.TimeUtils;

public class FlyB extends Check implements ClassInterface {
    public FlyB(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
    double lastY;
    boolean lastGround;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingEvent) {
            if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.getMountedTicks() > 0 || user.isSwitchedGamemodes() || user.getBlockData().liquidTicks > 0 || user.blockData.climbableTicks > 0 || TimeUtils.elapsed(user.lastFullTeleport) < 1000L || user.getPlayer().getAllowFlight()) {
                return;
            }
            CustomLocation to = user.getTo(), from = user.getFrom();
            double distY = to.getY() - from.getY(), predictedDist = (lastY - 0.08D) * 0.9800000190734863D;

            boolean serverOnGround = user.isOnGround(), serverLastGround = lastGround;
            boolean clientOnGround = user.isClientGround(), clientLastGround = user.isLastClientGround();

            if (Math.abs(predictedDist) <= 0.005D) {
                predictedDist = 0;
            }
            if ((!clientOnGround && !clientLastGround)) {
                if (Math.abs(distY - predictedDist) > 1E-12) {
                    flag(user, "" + Math.abs(distY - predictedDist) + " " + Math.abs(predictedDist));
                }
            }
            lastGround = serverOnGround;
            lastY = distY;
        }
    }
}
