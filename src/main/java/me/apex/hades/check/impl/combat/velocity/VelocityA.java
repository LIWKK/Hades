package me.apex.hades.check.impl.combat.velocity;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.event.impl.packetevents.VelocityEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "Velocity", type = "A")
public class VelocityA extends Check {

    int ticks;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof VelocityEvent) {
            ticks = 0;
        }
        if (e instanceof FlyingPacketEvent) {
            ticks++;

            long time = System.currentTimeMillis() - user.getLastMovePacket();
            if (time >= 100L) {
                return;
            }
            if (ticks == 2) {
                if (user.getBlockData().blockAboveTicks > 0 || TimeUtils.elapsed(user.lastTeleport) < 2000L) {
                    return;
                }
                double deltaY = Math.abs(user.getTo().getY() - user.getFrom().getY());
                double vertical = user.getVerticalVelocity();
                if (deltaY < vertical) {
                    flag(user, "Vertical Velocity: " + deltaY);
                }
            }
        }
    }
}