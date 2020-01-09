package me.apex.hades.check.impl.movement.jesus;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(Name = "Jesus (A)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class JesusA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(user.getPlayer().getAllowFlight()
                    || user.getPlayer().getNearbyEntities(2.0D, 3.0D, 2.0D).toString().contains("CraftBoat")
                    || PlayerUtils.isOnLilyOrCarpet(user.getPlayer()) || user.getPlayer().isInsideVehicle()) return;

            double distY = user.getDeltaY();
            boolean inLiquid = BlockUtils.isLiquid(user.getPlayer().getLocation().getBlock());

            if(Math.abs(distY) < 0.003 && inLiquid && !user.onGround() && !user.isLagging() && !PlayerUtils.blockNearHead(user.getPlayer()))
            {
                if(vl++ > 6)
                    flag(user, "distY = " + Math.abs(distY));
            }else vl = 0;
        }
    }

}
