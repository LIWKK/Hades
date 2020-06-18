package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

public class FlyC extends Check implements ClassInterface {
    public FlyC(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

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
