package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;

@CheckInfo(name = "Fly", type = "A")
public class FlyA extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.getDeltaY() >= 0.0
                    && user.getAirTicks() > 6
                    && user.getPlayer().getVelocity().getY() < -0.075
                    && elapsed(user.getTick(), user.getLiquidTick()) > 20
                    && !user.isInWeb()
                    && !user.isOnClimbableBlock()
                    && !PlayerUtil.isOnClimbable(user.getPlayer())
                    && !user.getPlayer().getAllowFlight()
                    && user.getPlayer().getVehicle() == null
                    && user.getTick() > 5
                    && elapsed(user.getTick(), user.getVelocityTick()) > 100
                    && !PlayerUtil.isClimbableBlock(user.getLocation().subtract(0,1,0).getBlock())) {
                    flag(user, "y motion higher than 0, m: " + user.getDeltaY());
            }
        }
    }

}