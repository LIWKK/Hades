package me.apex.hades.check.impl.combat.aim;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Aim", type = "G")
public class AimG extends Check {

    private float lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            float diff = Math.abs(user.getDeltaPitch()) % 180F;
            float lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff * Math.pow(2, 24)), (long) (lastDiff * Math.pow(2, 24)));
            double val = gcd / Math.pow(2, 24);

            if (val > 0.0D && val < 0.08D && !user.isUsingOptifine()) {
                reset(1000);
                if (vl++ > 4)
                    flag(user, "val = " + val);
            } else vl = 0;
        }
    }

}
