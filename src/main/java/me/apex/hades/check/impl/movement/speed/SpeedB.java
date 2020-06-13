package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Speed", type = "B")
public class SpeedB extends Check {
    int preVL = 0;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName()) && (System.currentTimeMillis() - user.getLastVelocity()) > 1000) {
            if (user.getTeleportTicks() > 0) return;

        }
    }
}
