package me.apex.hades.check.impl.combat.aim;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Aim", type = "B")
public class AimB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getType())) {
            if (user.getDeltaYaw() > 0.1 && user.getLocation().getYaw() == Math.round(user.getLocation().getYaw())) {
                if (vl++ > 1)
                    flag(user, "yaw = " + user.getLocation().getYaw());
            } else vl = 0;
        }
    }

}
