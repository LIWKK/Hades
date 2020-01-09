package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;

import java.util.Deque;
import java.util.LinkedList;

@CheckInfo(Name = "Aura (G)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AuraG extends Check {

    private Deque<Long> diffs = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY))
        {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if(packet.getAction() == packet.getAction().ATTACK)
            {
                diffs.add((long)user.getDeltaYaw());
                if(diffs.size() == 10)
                {
                    double deviation = MathUtils.getStandardDeviation(diffs.stream().mapToLong(l -> l).toArray());

                    if(deviation > 100)
                        flag(user, "deviation = " + deviation);

                    diffs.clear();
                }
            }
        }
    }

}
