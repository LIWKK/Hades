package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;
import me.apex.hades.utils.violation.Verbose;

public class FlyD extends Check implements ClassInterface {
    public FlyD(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
    Verbose verbose = new Verbose();

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (user != null) {
                if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.getBlockData().climbableTicks > 0 || user.getBlockData().liquidTicks > 0|| TimeUtils.elapsed(user.getLastFullTeleport()) < 1000L) {
                    return;
                }
                boolean clientGround = user.getPlayer().isOnGround(), serverGround = user.isOnGround();
                double speed = user.getDeltaXZ();
                if (!clientGround && user.getClientAirTicks() > 15 || !serverGround && user.getAirTicks() > 15) {
                    if (speed > 0.2873D) {
                        if (++preVL > 10) {
                            flag(user, "Air Speed");
                        }
                    }else preVL -= Math.min(preVL, 0.5);
                }
            }
        }
    }
}