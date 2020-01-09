package me.apex.hades.check.impl.movement.fly;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(Name = "Fly (B)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class FlyB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            double dist = user.getDeltaY();
            double lastDist = user.getLastDeltaY();
            double prediction = dist - (lastDist * 0.98D);

            if(prediction > -0.07D && !user.onGround() && !user.isLagging() && !BlockUtils.isClimbableBlock(user.getPlayer().getLocation().getBlock()) && !PlayerUtils.isInLiquid(user.getPlayer()) && !PlayerUtils.isInWeb(user.getPlayer()))
            {
                if(vl++ > 2)
                    flag(user, "dist = " + prediction);
            }else vl = 0;
        }
    }

}
