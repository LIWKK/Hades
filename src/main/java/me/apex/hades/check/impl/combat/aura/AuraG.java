package me.apex.hades.check.impl.combat.aura;

import java.util.Deque;
import java.util.LinkedList;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura", type = "G")
public class AuraG extends Check {

    private final Deque<Long> diffs = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK) {
                user.setHitTicks(5);
                diffs.add((long) user.getDeltaYaw());
                if (diffs.size() == 10) {
                    double deviation = MathUtils.getStandardDeviation(diffs.stream().mapToLong(l -> l).toArray());

                    if (deviation > 100)
                        flag(user, "deviation = " + deviation);

                    diffs.clear();
                }
            }
        }
    }

}
