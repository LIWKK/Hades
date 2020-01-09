package me.apex.hades.check.impl.packet.timer;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import java.util.Deque;
import java.util.LinkedList;

@CheckInfo(Name = "Timer (B)", Type = Check.CheckType.PACKET, Experimental = false)
public class TimerB extends Check {

    private Deque<Long> flyingDeque = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            flyingDeque.add(e.getTimeStamp());

            if(flyingDeque.size() == 50)
            {
                double deviation = MathUtils.getStandardDeviation(flyingDeque.stream().mapToLong(l -> l).toArray());

                if(deviation > 900)
                {
                    if(vl++ > 1)
                        flag(user, "deviation = " + deviation);
                }else vl = 0;

                flyingDeque.clear();
            }
        }
    }

}
