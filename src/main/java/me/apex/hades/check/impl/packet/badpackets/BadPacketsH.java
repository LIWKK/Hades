package me.apex.hades.check.impl.packet.badpackets;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "BadPackets", type = "H")
public class BadPacketsH extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
        	WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getPacket());

            int ticks = this.ticks;
            this.ticks = 0;

            if (ticks < 2 && PacketUtils.isBlockPacket(packet.getItemStack().getType().toString())) {
                if (vl++ > 4)
                    flag(user, "ticks = " + ticks);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
