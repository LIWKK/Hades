package me.apex.hades.check.impl.combat.aim;

import java.util.Deque;
import java.util.LinkedList;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Aim", type = "C")
public class AimC extends Check {

    private Deque<Long> diffs = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            double yawDiff = user.getDeltaYaw();
            double lastYawDiff = user.getLastDeltaYaw();
            double diff = Math.abs(yawDiff - lastYawDiff);

            diffs.add((long) diff);

            if (diffs.size() == 5) {
                double deviation = MathUtils.getStandardDeviation(diffs.stream().mapToLong(l -> l).toArray());

                if ((deviation > 100.0D) && yawDiff > 2.0D) {
                    if (vl++ > 2)
                        flag(user, "deviation = " + deviation);
                } else vl = 0;

                diffs.clear();
            }
        }
    }

}
