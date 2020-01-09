package me.apex.hades.check.impl.movement.speed;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.PlayerUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "Speed (D)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class SpeedD extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            double dist = user.getDeltaXZ();
            double lastDist = user.getLastDeltaXZ();

            if((lastDist < 0.1D && dist >= 0.287D) || (dist < 0.1D && lastDist >= 0.287D)
                    && !PlayerUtils.hasBlocksAround(user.getPlayer().getLocation()) && e.getTimeStamp() - user.getLastServerPosition() > 2000 && !user.isLagging())
            {
                if(++vl > 2)
                    flag(user, "dist = " + dist + ", lastDist = " + lastDist);
            }else vl -= vl > 0 ? 0.25 : 0;
        }
    }

}
