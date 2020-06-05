package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "F")
public class AuraF extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK) {
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
