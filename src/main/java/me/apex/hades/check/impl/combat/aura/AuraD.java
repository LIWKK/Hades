package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "Aura", type = "D")
public class AuraD extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if (packet.getAction() == packet.getAction().ATTACK) {
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
