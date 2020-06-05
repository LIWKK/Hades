package me.apex.hades.check.impl.packet.timer;

import java.util.Deque;
import java.util.LinkedList;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Timer", type = "B")
public class TimerB extends Check {

    private Deque<Long> flyingDeque = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            flyingDeque.add(e.getTimestamp());

            if (flyingDeque.size() == 50) {
                double deviation = MathUtils.getStandardDeviation(flyingDeque.stream().mapToLong(l -> l).toArray());

                if (deviation > 900) {
                    if (vl++ > 1)
                        flag(user, "deviation = " + deviation);
                } else vl = 0;

                flyingDeque.clear();
            }
        }
    }

}
