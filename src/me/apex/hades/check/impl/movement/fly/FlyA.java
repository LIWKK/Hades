package me.apex.hades.check.impl.movement.fly;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(Name = "Fly (A)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class FlyA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            double dist = user.getDeltaY();
            double lastDist = user.getLastDeltaY();
            double diff = dist - lastDist;

            if(!user.onGround() && !user.isLagging() && !BlockUtils.isClimbableBlock(user.getPlayer().getLocation().getBlock()) && !PlayerUtils.isInLiquid(user.getPlayer()) && !PlayerUtils.isInWeb(user.getPlayer()))
            {
                if(diff >= 0.0D) {
                    if (vl++ > 4)
                        flag(user, "diff = " + diff);
                }
            }else vl = 0;
        }
    }

}
