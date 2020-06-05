package me.apex.hades.check.impl.packet.badpackets;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "BadPackets", type = "E")
public class BadPacketsE extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.HELD_ITEM_SLOT)) {
            int ticks = this.ticks;
            this.ticks = 0;

            if (ticks < 1) {
                if (vl++ > 2)
                    flag(user, "ticks = " + ticks);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
