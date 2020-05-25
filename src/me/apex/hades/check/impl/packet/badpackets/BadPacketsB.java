package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "BadPackets", type = "B")
public class BadPacketsB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.ABILITIES)) {
            if (!user.getPlayer().getAllowFlight()) {
                flag(user, "allowed = " + user.getPlayer().getAllowFlight());
            }
        }
    }

}
