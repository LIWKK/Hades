package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.use_entity.impl.WrappedPacketInUseEntity;

@CheckInfo(name = "BadPackets", type = "C")
public class BadPacketsC extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.action == EntityUseAction.ATTACK) {
                int ticks = this.ticks;
                this.ticks = 0;

                if (ticks < 1 && !user.isLagging()) {
                    if (vl++ > 1)
                        flag(user, "ticks = " + ticks);
                } else vl = 0;
            }
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
