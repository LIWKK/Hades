package me.apex.hades.check.impl.packet.nofall;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.WrappedPacketPlayInFlying;

@CheckInfo(name = "NoFall", type = "A")
public class NoFallA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION) || e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK)) {
            WrappedPacketPlayInFlying packet = new WrappedPacketPlayInFlying(e.getPacket());
            if (packet.f && !user.onGround() && !user.isLagging()) {
                if (vl++ > 4)
                    flag(user, "client = " + packet.f + ", server = " + user.onGround());
            } else vl = 0;
        }
    }

}
