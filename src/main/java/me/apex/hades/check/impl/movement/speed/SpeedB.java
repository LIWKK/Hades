package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(name = "Speed", type = "B")
public class SpeedB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName()) && (System.currentTimeMillis() - user.getLastVelocity()) > 1000) {
            if (user.getTeleportTicks() > 0) return;

            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();

            double prediction = lastDist * 0.699999988079071D;
            double diff = Math.abs(dist - prediction);
            double scaledDist = diff * 100;

            double max = PlayerUtils.getBaseMovementSpeed(user, 9.9D, true);

            if (scaledDist > max && !user.getPlayer().getAllowFlight()) {
                if (vl++ > 3)
                    flag(user, "dist = " + scaledDist);
            } else vl = 0;
        }
    }
}
