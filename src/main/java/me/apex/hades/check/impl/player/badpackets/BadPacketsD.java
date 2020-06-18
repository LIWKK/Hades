package me.apex.hades.check.impl.player.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

//Remaking this check soon!
@CheckInfo(name = "BadPackets", type = "D")
public class BadPacketsD extends Check {

    private double lastX, lastY, lastZ;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent){
            FlyingEvent packet = (FlyingEvent)e;
            if (packet.hasMoved()){
                double curX = packet.getX(),
                        curY = packet.getY(),
                        curZ = packet.getZ();

                if (Math.abs(curX - lastX) >= 10
                        || Math.abs(curY - lastY) >= 10
                        || Math.abs(curZ - lastZ) >= 10
                        && user.teleportTick == 0) {
                    if (++preVL > 1)
                        flag(user, "curXYZ = " + curX + ", " + curY + ", " + curZ + ", lastXYZ = " + lastX + ", " + lastY + ", " + lastZ);
                } else preVL = 0;

                this.lastX = curX;
                this.lastY = curY;
                this.lastZ = curZ;
            }
        }
    }
}
