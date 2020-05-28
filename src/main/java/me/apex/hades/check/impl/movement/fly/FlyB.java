package me.apex.hades.check.impl.movement.fly;

import org.bukkit.Bukkit;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            if (e.getTimestamp() - user.getLastVelocity() < 2000 || user.getPlayer().getAllowFlight() || PlayerUtils.isClimbableBlock(user.getLocation().getBlock()) || PlayerUtils.isInWeb(user.getPlayer()) || PlayerUtils.isInLiquid(user.getPlayer())) {
                vl = 0;
                return;
            }

            double dist = user.getDeltaY() - user.getLastDeltaY();

            if (!user.isNearGround()) {
                if (dist >= 0.0D) {
                	if(vl++ > 10) {
                		flag(user, "dist = " + dist);
                	}
                }
            } else vl = 0;
        }
    }

}
