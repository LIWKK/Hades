package me.apex.hades.check.impl.combat.aim;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Aim", type = "E")
public class AimE extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            float diff = Math.abs(user.getDeltaYaw()) % 360F;
            if (diff < 0.08F && diff > 0.0F && !user.isUsingOptifine()) {
                if (vl++ > 2)
                    flag(user, "yawDiff = " + user.getDeltaYaw());
            } else vl = 0;
        }
    }

}
