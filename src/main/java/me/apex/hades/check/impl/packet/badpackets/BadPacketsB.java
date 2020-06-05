package me.apex.hades.check.impl.packet.badpackets;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "BadPackets", type = "B")
public class BadPacketsB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ABILITIES)) {
            if (!user.getPlayer().getAllowFlight()) {
                flag(user, "allowed = " + user.getPlayer().getAllowFlight());
            }
        }
    }

}
