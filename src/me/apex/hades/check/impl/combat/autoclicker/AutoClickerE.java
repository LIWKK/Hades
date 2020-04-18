package me.apex.hades.check.impl.combat.autoclicker;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.utils.MathUtils;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerE extends Check {

    public AutoClickerE(User user) {
        super(user);
        name = "AutoClicker (E)";
        type = CheckType.COMBAT;
        experimental = true;
    }

    private long lastSwing, lastDiff;
    private Deque<Long> values = new LinkedList();

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacketHandler().getType() == Packet.Client.ARM_ANIMATION)
            {
                long swing = System.currentTimeMillis();
                long lastSwing = this.lastSwing;
                this.lastSwing = swing;

                long diff = Math.abs(swing - lastSwing);
                long lastDiff = this.lastDiff;
                this.lastDiff = diff;

                double val = MathUtils.gcd(diff, lastDiff) % Math.IEEEremainder(diff, lastDiff);

                values.add((long)val);

                if(values.size() == 5)
                {
                    double deviation = MathUtils.getStandardDeviation(values.stream().mapToLong(l -> l).toArray());

                    debug("deviation = " + deviation);

                    if(deviation == 0.0 && !user.data.digging)
                    {
                        if(++vl > 2)
                            flag("deviation = " + deviation);
                    }else vl = 0;

                    values.clear();
                }
            }
        }
    }

}
