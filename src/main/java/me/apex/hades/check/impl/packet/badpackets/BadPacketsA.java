package me.apex.hades.check.impl.packet.badpackets;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "BadPackets", type = "A")
public class BadPacketsA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
        	WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            if (Math.abs(packet.getPitch()) > 90.0F)
                flag(user, "pitch = " + packet.getPitch());
        }
    }

}
