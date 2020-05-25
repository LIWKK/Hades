package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.WrappedPacketPlayInUseEntity;

@CheckInfo(name = "Aura", type = "E")
public class AuraE extends Check {

    private double lastFixedGcd;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedPacketPlayInUseEntity packet = new WrappedPacketPlayInUseEntity(e.getPacket());
            if (packet.action == EntityUseAction.ATTACK) {
                double yawDiff = user.getDeltaYaw();
                double lastYawDiff = user.getLastDeltaYaw();

                double gcd = MathUtils.gcd((long) yawDiff, (long) lastYawDiff) * (yawDiff % lastYawDiff);
                double fixedGcd = gcd / Math.PI;
                double lastFixedGcd = this.lastFixedGcd;
                this.lastFixedGcd = fixedGcd;

                double diff = Math.abs(fixedGcd - lastFixedGcd);

                if ((diff < 1.0 || diff > 100.0D) && yawDiff > 2.0D) {
                    if (vl++ > 3)
                        flag(user, "diff = " + diff);
                } else vl = 0;
            }
        }
    }

}
