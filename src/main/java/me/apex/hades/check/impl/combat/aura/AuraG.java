package me.apex.hades.check.impl.combat.aura;

import java.util.Deque;
import java.util.LinkedList;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "G")
public class AuraG extends Check {

    private Deque<Long> diffs = new LinkedList();

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
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
