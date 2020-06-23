package me.apex.hades.check.impl.combat.velocity;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.VelocityEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Velocity", type = "A")
public class VelocityA extends Check {

    private double lastVertical;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof VelocityEvent) {
            if (((VelocityEvent) e).getEntityId() == user.getPlayer().getEntityId()) {
                if (((VelocityEvent) e).getVelY() > 0.2) {
                    lastVertical = ((VelocityEvent) e).getVelY();
                }
            }
        } else if (e instanceof FlyingEvent) {
            if (user.isTakingVelocity()) {
                if (user.getDeltaY() <= lastVertical * 0.99
                        && elapsed(user.getTick(), user.getUnderBlockTick()) > 20
                        && elapsed(user.getTick(), user.getLiquidTick()) > 20
                        && elapsed(user.getTick(), user.getLiquidTick()) > 20) {
                    flag(user, "didnt take expected velocity, d: " + user.getDeltaY() + ", v: " + lastVertical);
                }
            }
        }
    }

}
