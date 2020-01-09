package me.apex.hades.check.impl.combat.aim;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "Aim (A)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AimA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            if(user.getDeltaPitch() > 0.1 && user.getLocation().getPitch() == Math.round(user.getLocation().getPitch()))
            {
                if(vl++ > 1)
                    flag(user, "pitch = " + user.getLocation().getPitch());
            }else vl = 0;
        }
    }

}
