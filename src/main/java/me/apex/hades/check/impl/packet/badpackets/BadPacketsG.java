package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "BadPackets", type = "G")
public class BadPacketsG extends Check {

    private boolean lastWasArm;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if (!lastWasArm && packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
                if (vl++ > 1)
                    flag(user, "swung = " + lastWasArm);
            } else vl = 0;
        } else if (e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            lastWasArm = true;
        } else if (PacketUtils.isFlyingPacket(e.getType())) {
            lastWasArm = false;
        }
    }

}
