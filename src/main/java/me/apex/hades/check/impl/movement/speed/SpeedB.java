package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Speed", type = "B")
public class SpeedB extends Check  {

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            if(((FlyingPacketEvent) e).isClientMoved()) {
                double max = 0.34;
                max *= user.getPlayer().getWalkSpeed() / 0.2;
                max += user.getSpeedPotionEffectLevel() % max;

                if(user.getVelocityTicks() > 0) max += 0.21;
                if(user.getBlockData().iceTicks > 0) max += 0.34;
                if(user.getBlockData().blockAboveTicks > 0) max += 0.91;

                if(user.getDeltaXZ() > max
                        && !user.getPlayer().getAllowFlight()) {
                    if(++preVL > 7) {
                        flag(user, "too fast speed, s: " + user.getDeltaXZ());
                    }
                }else preVL *= 0.75;
            }
        }
    }
}
