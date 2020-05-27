package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "BadPackets", type = "F")
public class BadPacketsF extends Check {

    private double lastX, lastY, lastZ;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION) || e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK)) {
        	WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());

            double curX = packet.x;
            double lastX = this.lastX;
            this.lastX = curX;

            double curY = packet.y;
            double lastY = this.lastY;
            this.lastY = curY;

            double curZ = packet.z;
            double lastZ = this.lastZ;
            this.lastZ = curZ;

            if (Math.abs(curX - lastX) >= 10 || Math.abs(curY - lastY) >= 10 || Math.abs(curZ - lastZ) >= 10 && e.getTimestamp() - user.getLastServerPosition() > 2000) {
                if (++vl > 1)
                    flag(user, "curXYZ = " + curX + ", " + curY + ", " + curZ + ", lastXYZ = " + lastX + ", " + lastY + ", " + lastZ);
            } else vl = 0;
        }
    }

}
