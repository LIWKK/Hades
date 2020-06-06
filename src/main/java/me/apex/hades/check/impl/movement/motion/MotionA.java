package me.apex.hades.check.impl.movement.motion;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Motion", type = "A")
public class MotionA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            //if (user.getTeleportTicks() > 0 || user.getLocation().subtract(0,0.1,0).getBlock().getType() == Material.SNOW ||  user.isInLiquid()) return;

            //double dist = user.getDeltaY();
            // lastDist = user.getLastDeltaY();

            //if (dist >= 1.0 && lastDist == 0.0D && user.getPlayer().getVelocity().getY() < -0.075D)
            //    flag(user, "dist = " + dist + ", lastDist = " + lastDist);
        }
    }

}
