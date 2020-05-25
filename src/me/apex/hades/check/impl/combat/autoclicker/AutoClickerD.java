package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;

@CheckInfo(name = "AutoClicker", type = "D")
public class AutoClickerD extends Check {

    private int ticks, lastTicks;
    private double lastDelay;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            int ticks = this.ticks;
            this.ticks = 0;

            int lastTicks = this.lastTicks;
            this.lastTicks = ticks;

            double delay = Math.abs(ticks - lastTicks);
            double lastDelay = this.lastDelay;
            this.lastDelay = delay;

            try {
                double lcd = MathUtils.lcd((long) delay, (long) lastDelay);
                double fixedLcd = lcd * Math.PI;
                double remainder = Math.IEEEremainder(lcd, lastDelay) / Math.PI;

                if (Double.isNaN(remainder)) {
                    if (vl++ > 2)
                        flag(user, "remainder = " + remainder);
                } else vl -= vl > 0 ? 0.5 : 0;
            } catch (Exception ex) {
            }

        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
