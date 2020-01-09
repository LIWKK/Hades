package me.apex.hades.check.impl.movement.fly;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(Name = "Fly (C)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class FlyC extends Check {

    private double lastGround;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(user.onGround() || e.getTimeStamp() - user.getLastServerPosition() < 1000 || user.getPlayer().getVelocity().getY() > -0.075D || BlockUtils.isClimbableBlock(user.getPlayer().getLocation().getBlock()) || PlayerUtils.isInLiquid(user.getPlayer()) || PlayerUtils.isInWeb(user.getPlayer()))
                lastGround = user.getLocation().getY();

            if(lastGround == 0.0D) return;

            double max = 1.6D;
            double dist = user.getLocation().getY() - lastGround;

            if(dist > max && !user.onGround() && !user.isLagging())
            {
                if(vl++ > 4)
                    flag(user, "dist = " + dist);
            }else vl = 0;
        }
    }

}
