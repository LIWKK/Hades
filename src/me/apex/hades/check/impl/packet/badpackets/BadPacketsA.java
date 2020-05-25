package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packetwrappers.in.WrappedPacketPlayInFlying;

@CheckInfo(name = "BadPackets", type = "A")
public class BadPacketsA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
        	WrappedPacketPlayInFlying packet = new WrappedPacketPlayInFlying(e.getPacket());
            if (Math.abs(packet.pitch) > 90.0F)
                flag(user, "pitch = " + packet.pitch);
        }
    }

}
