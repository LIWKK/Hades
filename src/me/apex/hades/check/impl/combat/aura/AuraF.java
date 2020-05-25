package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "F")
public class AuraF extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if (packet.getAction() == packet.getAction().ATTACK) {
                double yawDiff = user.getDeltaYaw();
                double lastYawDiff = user.getLastDeltaYaw();

                try {
                    double lcd = MathUtils.lcd((long) yawDiff, (long) lastYawDiff);
                    double fixedLcd = lcd / Math.PI;
                    double remainder = Math.IEEEremainder(fixedLcd, lastYawDiff);
                    double offset = remainder * Math.E;

                    if (yawDiff < 10 && offset < -4.2D) {
                        if (vl++ > 3)
                            flag(user, "offset = " + offset);
                    } else vl = 0;
                } catch (Exception ex) {
                }
            }
        }
    }

}
