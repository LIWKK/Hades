package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;

@CheckInfo(name = "AutoClicker", type = "A")
public class AutoClickerA extends Check {

    private int ticks;
    private double avgClickSpeed, lastCps;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            int ticks = this.ticks;
            this.ticks = 0;

            if (user.isDigging() || ticks > 5) return;

            double clickSpeed = ticks * 50.0D;
            avgClickSpeed = (((avgClickSpeed * 19.0D) + clickSpeed) / 20.0D);

            double cps = 1000.0D / avgClickSpeed;
            double lastCps = this.lastCps;
            this.lastCps = cps;

            if (MathUtils.isRoughlyEqual(cps, lastCps, 0.001) && cps > 2.6 && !user.isLagging()) {
                if (vl++ > 12)
                    flag(user, "cps = " + cps + ", lastCps = " + lastCps);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
