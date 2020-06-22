package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;

@CheckInfo(name = "Speed", type = "D")
public class SpeedD extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if(e instanceof FlyingEvent) {
            double diff = user.getDeltaXZ() - user.getLastDeltaXZ();
            if(diff > MathUtil.getBaseSpeed(user.getPlayer()) && !user.isTakingVelocity() && !user.getPlayer().isInsideVehicle()) {
                flag(user, "invalid acceleration, a: " + diff);
            }
        }
    }
}
