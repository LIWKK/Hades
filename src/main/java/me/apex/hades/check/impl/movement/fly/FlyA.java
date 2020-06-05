package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(name = "Fly", type = "A")
public class FlyA extends Check {

    private double lastGround;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName()) && !user.getFlyAFix()) {
            if (user.onGround()) {
                lastGround = user.getLocation().getY();
            } else {
                if (user.getTeleportTicks() > 0 || user.getPlayer().getAllowFlight() || PlayerUtils.isClimbableBlock(user.getLocation().getBlock()) || PlayerUtils.isInWeb(user.getPlayer()) || PlayerUtils.isInLiquid(user.getPlayer())) {
                    vl = 0;
                    return;
                }

                double dist = user.getLocation().getY() - lastGround;
                double velocity = user.getPlayer().getVelocity().getY();

                if (dist >= 1.3 && user.getLocation().getY() >= user.getLastLocation().getY() && velocity < -0.06D && user.getPlayer().getVehicle() == null) {
                    if (vl++ > 9)
                        flag(user, "curY = " + user.getLocation().getY() + ", lastGround = " + lastGround);
                } else vl = 0;
            }
        }
    }

}
