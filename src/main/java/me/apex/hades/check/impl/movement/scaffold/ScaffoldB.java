package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Scaffold", type = "B")
public class ScaffoldB extends Check {

    private long lastFlying;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
            long timeDiff = Math.abs(e.getTimestamp() - lastFlying);

            if (timeDiff < 5) {
                if (vl++ > 10)
                    flag(user, "diff = " + timeDiff);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            lastFlying = e.getTimestamp();
        }
    }

}
