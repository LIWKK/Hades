package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura", type = "D")
public class AuraD extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK) {
                double yawDiff = user.getDeltaYaw();
                double lastYawDiff = user.getLastDeltaYaw();

                double remainder = Math.IEEEremainder(yawDiff, lastYawDiff);
                double hypot = Math.hypot(yawDiff, lastYawDiff);

                int offset = (int) ((remainder * hypot) / Math.PI);

                if ((offset < -2 && offset > -7) || (offset < -10 && offset > -15) && Math.abs(yawDiff) > 0) {
                    if (++vl > 5)
                        flag(user, "offset = " + offset);
                } else vl = 0;
            }
        }
    }

}
