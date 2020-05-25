package me.apex.hades.check.impl.combat.velocity;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Velocity", type = "B")
public class VelocityB extends Check {

    private double lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            if (e.getTimestamp() - user.getLastVelocity() < 500 && (user.getLastVelX() > 0.1 || user.getLastVelZ() > 0.1)) {
                double diff = MathUtils.sigmoid(user.getDeltaXZ() - ((user.getLastVelX() * user.getLastVelX()) - (user.getLastVelZ() * user.getLastVelZ()))) * 0.4D;
                double lastDiff = this.lastDiff;
                this.lastDiff = diff;

                if (PlayerUtils.isClimbableBlock(user.getLocation().getBlock())
                        || PlayerUtils.isInWeb(user.getPlayer())
                        || PlayerUtils.isInLiquid(user.getPlayer())
                        || PlayerUtils.blockNearHead(user.getPlayer())) {
                    vl = 0;
                    return;
                }

                if (diff == lastDiff || (diff < 0.19D && lastDiff < 0.19D)) {
                    if (vl++ > 2)
                        flag(user, "diff = " + diff + ", lastDiff = " + lastDiff);
                } else vl = 0;
            }
        }
    }

}
