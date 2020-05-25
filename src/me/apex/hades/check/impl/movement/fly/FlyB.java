package me.apex.hades.check.impl.movement.fly;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getType())) {
            if (e.getTimeStamp() - user.getLastVelocity() < 2000 || user.getPlayer().getAllowFlight() || BlockUtils.isClimbableBlock(user.getLocation().getBlock()) || PlayerUtils.isInWeb(user.getPlayer()) || PlayerUtils.isInLiquid(user.getPlayer())) {
                vl = 0;
                return;
            }

            double dist = user.getDeltaY() - user.getLastDeltaY();

            if (!user.onGround() && !user.isLagging()) {
                if (dist >= 0.0D) {
                    if (vl++ > 4)
                        flag(user, "dist = " + dist);
                }
            } else vl = 0;
        }
    }

}
