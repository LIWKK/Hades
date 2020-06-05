package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "H")
public class AuraH extends Check {

    public long lastHit;
    public double pitch, lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK) {
                lastHit = e.getTimestamp();
            }
        }
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK) || e.getPacketName().equalsIgnoreCase(Packet.Client.LOOK)) {

            double pitch = e.getPlayer().getLocation().getPitch();
            double lastPitch = this.pitch;
            this.pitch = pitch;

            double diff = pitch - lastPitch;
            double lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff * Math.pow(2.0, 24.0)), (long) (lastDiff * Math.pow(2.0, 24.0)));

            if (gcd > 0 && e.getTimestamp() - lastHit < 1000 && (gcd / Math.pow(2.0, 24.0)) > 0 && (gcd / Math.pow(2.0, 24.0)) < 0.01 && !user.isUsingOptifine()) {
                reset(1000);
                if (vl++ > 8) {
                    flag(user, "simple = " + (gcd / Math.pow(2.0, 24.0)));
                }
            }

        }
    }

}
