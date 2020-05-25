package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.WrappedPacketPlayInUseEntity;

@CheckInfo(name = "BadPackets", type = "G")
public class BadPacketsG extends Check {

    private boolean lastWasArm;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedPacketPlayInUseEntity packet = new WrappedPacketPlayInUseEntity(e.getPacket());
            if (!lastWasArm && packet.action == EntityUseAction.ATTACK) {
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
