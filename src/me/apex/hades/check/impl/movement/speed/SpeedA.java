package me.apex.hades.check.impl.movement.speed;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(Name = "Speed (A)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class SpeedA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(e.getTimeStamp() - user.getLastServerPosition() < 1000) return;

            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();

            double diff = Math.abs(dist - lastDist);

            if(diff == 0.0D && dist > PlayerUtils.getBaseMovementSpeed(user, 0.29D, false) && !user.isLagging())
                flag(user, "diff = " + diff);
        }
    }

}
