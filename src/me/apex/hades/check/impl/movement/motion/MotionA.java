package me.apex.hades.check.impl.movement.motion;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "Motion (A)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class MotionA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(e.getTimeStamp() - user.getLastServerPosition() < 1000) return;

            double dist = user.getDeltaY();
            double lastDist = user.getLastDeltaY();

            if(dist >= 1.0 && lastDist == 0.0D && user.getPlayer().getVelocity().getY() < -0.075D && !user.isLagging())
                flag(user, "dist = " + dist + ", lastDist = " + lastDist);
        }
    }

}
