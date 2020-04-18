package me.apex.hades.check.impl.movement.motion;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInFlyingPacket;
import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.utils.PlayerUtils;

public class MotionD extends Check {
    public MotionD(User user) {
        super(user);
        name = "Motion (D)";
        type = CheckType.MOVEMENT;
    }

    private double lastX, lastY, lastZ;

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacketHandler().getType() == Packet.Client.POSITION)
            {
                WrappedInFlyingPacket packet = new WrappedInFlyingPacket(e.getPacketHandler().getPacket(), e.getPacketHandler().getPlayer());

                double curX = packet.getX();
                double lastX = this.lastX;

                double curY = packet.getY();
                double lastY = this.lastY;

                double curZ = packet.getZ();
                double lastZ = this.lastZ;

                this.lastX = curX;
                this.lastY = curY;
                this.lastZ = curZ;

                if(Math.abs(curX - lastX) >= 10 || Math.abs(curY - lastY) >= 10 || Math.abs(curZ - lastZ) >= 10 && PlayerUtils.timeSinceTeleport(user) > 2000)
                {
                    if(++vl > 1)
                        flag("curXYZ = " + curX + ", " + curY + ", " + curZ + ", lastXYZ = " + lastX + ", " + lastY + ", " + lastZ);
                }else
                    vl = 0;
            }
        }
    }
}
