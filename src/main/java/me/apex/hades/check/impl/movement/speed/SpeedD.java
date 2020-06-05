package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(name = "Speed", type = "D")
public class SpeedD extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();

            if ((lastDist < 0.1D && dist >= 0.287D) || (dist < 0.1D && lastDist >= 0.287D)
                    && !PlayerUtils.hasBlocksAround(user.getPlayer().getLocation()) && user.getTeleportTicks() == 0 && !user.getPlayer().getAllowFlight()) {
                if (++vl > 2)
                    flag(user, "dist = " + dist + ", lastDist = " + lastDist);
            } else vl -= vl > 0 ? 0.25 : 0;
        }
    }

}
