package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.utils.MathUtils;

import java.util.Deque;
import java.util.LinkedList;

public class AuraH extends Check {

    public AuraH(User user) {
        super(user);
        name = "Aura (H)";
        type = CheckType.COMBAT;
    }

    private Deque<Long> diffs = new LinkedList();

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacketHandler().getType() == Packet.Client.USE_ENTITY)
            {
                diffs.add((long)user.data.deltaYaw);
                if(diffs.size() == 10)
                {
                    double deviation = MathUtils.getStandardDeviation(diffs.stream().mapToLong(l -> l).toArray());

                    debug("deviation = " + deviation);

                    if(deviation > 100)
                        flag("deviation = " + deviation);

                    diffs.clear();
                }
            }
        }
    }

}
