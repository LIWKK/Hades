package me.apex.hades.check.impl.movement.motion;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "Motion (B)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class MotionB extends Check {

    private long lastCancel;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(e.getTimeStamp() - user.getLastServerPosition() < 1000) return;
            if(BlockUtils.isLiquid(user.getPlayer().getLocation().getBlock())) lastCancel = e.getTimeStamp();
            if(e.getTimeStamp() - lastCancel < 600 || e.getTimeStamp() - user.getLastOnSlime() < 600) return;

            double dist = user.getDeltaY();
            double lastDist = user.getLastDeltaY();

            if(dist != lastDist && dist < 0.4 && lastDist < 0.4
                    && (dist > 0.0D || lastDist > 0.0D) && (dist < 0.0D && lastDist < 0.0D) && dist != 0.0D && lastDist != 0.0D
                    && !user.getPlayer().isFlying() && !user.isLagging())
            {
                if(vl++ > 2)
                    flag(user, "dist = " + dist + ", lastDist = " + lastDist);
            }else vl = 0;
        }
    }

}
