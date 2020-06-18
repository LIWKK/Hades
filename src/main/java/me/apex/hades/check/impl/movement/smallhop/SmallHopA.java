package me.apex.hades.check.impl.movement.smallhop;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "SmallHop", type = "A")
public class SmallHopA extends Check {

    private double lastDeltaY;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            if(((FlyingPacketEvent) e).isClientMoved()) {
                double deltaY = user.getLocation().getY() - user.getPreviousLocation().getY();
                double lastDeltaY = this.lastDeltaY;
                this.lastDeltaY = deltaY;

                if(deltaY == -lastDeltaY && deltaY != 0.0) {
                    if(++preVL > 1) {
                        flag(user, "repetitive vertical motions, m: " + deltaY);
                    }
                }else preVL = 0;
            }
        }
    }
}
