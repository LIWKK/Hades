package me.apex.hades.check.impl.combat.aim;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Aim", type = "F")
public class AimF extends Check {

    private float lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            float diff = Math.abs(user.getDeltaYaw()) % 360F;
            float lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff * Math.pow(2, 24)), (long) (lastDiff * Math.pow(2, 24)));
            double val = gcd / Math.pow(2, 24);

            if (val > 0.0D && val < 0.0015D && !user.isUsingOptifine()) {
                if (vl++ > 4)
                    flag(user, "val = " + val);
            } else vl = 0;
        }
    }

}
