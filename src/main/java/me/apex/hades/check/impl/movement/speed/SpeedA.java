package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "A")
public class SpeedA extends Check {

    private double lastDeltaXZ;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            if(((FlyingPacketEvent) e).isClientMoved()) {
                double max = 0.29;
                max *= user.getPlayer().getWalkSpeed() / 0.2;
                max += user.getSpeedPotionEffectLevel() % max;

                double deltaXZ = user.getDeltaXZ();
                double lastDeltaXZ = this.lastDeltaXZ;
                this.lastDeltaXZ = deltaXZ;

                double diff = Math.abs(deltaXZ - lastDeltaXZ);

                if(diff == 0.0 && deltaXZ > max
                        && !user.getPlayer().getAllowFlight()) {
                    flag(user, "consistent speed, d: " + diff);
                }
            }
        }
    }
}
