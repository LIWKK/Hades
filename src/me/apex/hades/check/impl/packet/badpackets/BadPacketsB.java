package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;

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
