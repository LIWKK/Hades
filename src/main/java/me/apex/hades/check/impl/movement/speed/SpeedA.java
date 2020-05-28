package me.apex.hades.check.impl.movement.speed;

import org.bukkit.Bukkit;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Speed", type = "A")
public class SpeedA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            if (e.getTimestamp() - user.getLastServerPosition() < 1000) return;

            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();

            double diff = Math.abs(dist - lastDist);

            if (diff == 0.0D && !user.getPlayer().getAllowFlight() && dist > PlayerUtils.getBaseMovementSpeed(user, 0.29D, false))
                flag(user, "diff = " + diff);
        }
    }

}
