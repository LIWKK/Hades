package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;

@CheckInfo(name = "BadPackets", type = "D")
public class BadPacketsD extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            int ticks = this.ticks;
            this.ticks = 0;

            if (ticks < 1 && !user.isLagging()) {
                if (vl++ > 2)
                    flag(user, "ticks = " + ticks);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
