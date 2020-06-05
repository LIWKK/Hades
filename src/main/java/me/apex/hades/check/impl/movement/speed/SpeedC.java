package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Speed", type = "C")
public class SpeedC extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName()) && (System.currentTimeMillis() - user.getLastVelocity()) > 1000) {
            if (user.getTeleportTicks() > 0) return;

            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();
            double gcd = MathUtils.gcd((long) dist, (long) lastDist) / (dist - lastDist);

            double max = 0.0D;
            if (PlayerUtils.blockNearHead(user.getPlayer()))
                max = 15.0D;

            if (gcd > max && !user.getPlayer().getAllowFlight())
                flag(user, "dist = " + gcd);
        }
    }

}
