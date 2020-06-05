package me.apex.hades.check.impl.packet.badpackets;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "BadPackets", type = "G")
public class BadPacketsG extends Check {

    private boolean lastWasArm;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (!lastWasArm && packet.getAction() == EntityUseAction.ATTACK) {
                if (vl++ > 1)
                    flag(user, "swung = " + lastWasArm);
            } else vl = 0;
        } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            lastWasArm = true;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            lastWasArm = false;
        }
    }

}
