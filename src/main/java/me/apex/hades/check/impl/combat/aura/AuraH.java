package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "H")
public class AuraH extends Check {

    public long lastHit;
    public double pitch, lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent event, User user) {
        if (event.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(event.getPacket(), event.getPlayer());
            if (packet.getAction() == packet.getAction().ATTACK) {
                lastHit = event.getTimeStamp();
            }
        }
        if (event.getType().equalsIgnoreCase(Packet.Client.POSITION_LOOK) || event.getType().equalsIgnoreCase(Packet.Client.LOOK)) {

            double pitch = event.getPlayer().getLocation().getPitch();
            double lastPitch = this.pitch;
            this.pitch = pitch;

            double diff = pitch - lastPitch;
            double lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff * Math.pow(2.0, 24.0)), (long) (lastDiff * Math.pow(2.0, 24.0)));

            if (gcd > 0 && event.getTimeStamp() - lastHit < 1000 && (gcd / Math.pow(2.0, 24.0)) > 0 && (gcd / Math.pow(2.0, 24.0)) < 0.01 && !user.isUsingOptifine()) {
                reset(1000);
                if (vl++ > 8) {
                    flag(user, "simple = " + (gcd / Math.pow(2.0, 24.0)));
                }
            }

        }
    }

}
