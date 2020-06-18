package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Fly", type = "C")
public class FlyC extends Check {
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            double deltaY = user.getLocation().getY() - user.getPreviousLocation().getY();
            if(deltaY >= 0.0
                    && !user.isOnGround()
                    && user.getAirTicks() > 8
                    && user.getBlockData().webTicks < 1
                    && user.getBlockData().liquidTicks < 1
                    && user.getBlockData().climbableTicks < 1) {
                flag(user, "y motion higher than 0, m: " + deltaY);
            }
        }
    }
}
