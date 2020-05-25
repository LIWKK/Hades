package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInFlyingPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "BadPackets", type = "F")
public class BadPacketsF extends Check {

    private double lastX, lastY, lastZ;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.POSITION) || e.getType().equalsIgnoreCase(Packet.Client.POSITION_LOOK)) {
            WrappedInFlyingPacket packet = new WrappedInFlyingPacket(e.getPacket(), e.getPlayer());

            double curX = packet.getX();
            double lastX = this.lastX;
            this.lastX = curX;

            double curY = packet.getX();
            double lastY = this.lastY;
            this.lastY = curY;

            double curZ = packet.getX();
            double lastZ = this.lastZ;
            this.lastZ = curZ;

            if (Math.abs(curX - lastX) >= 10 || Math.abs(curY - lastY) >= 10 || Math.abs(curZ - lastZ) >= 10 && e.getTimeStamp() - user.getLastServerPosition() > 2000) {
                if (++vl > 1)
                    flag(user, "curXYZ = " + curX + ", " + curY + ", " + curZ + ", lastXYZ = " + lastX + ", " + lastY + ", " + lastZ);
            } else vl = 0;
        }
    }

}
