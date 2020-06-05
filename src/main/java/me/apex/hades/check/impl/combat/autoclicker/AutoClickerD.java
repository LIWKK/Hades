package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "AutoClicker", type = "D")
public class AutoClickerD extends Check {

	public AutoClickerD() { dev = true; }
	
    private int ticks, lastTicks;
    private double lastDelay;
    boolean digging;

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

            if (delay != 0 || lastDelay != 0) {
                double lcd = MathUtils.lcd((long) delay, (long) lastDelay);
                double fixedLcd = lcd * Math.PI;
                double remainder = Math.IEEEremainder(lcd, lastDelay) / Math.PI;

                if (!user.isDigging()) {
                    if (Double.isNaN(remainder)) {
                        if (vl++ > 2.5) {
                            flag(user, "remainder = " + remainder);
                        }
                    } else vl -= vl > 0 ? 0.5 : 0;
                }
            } else vl *= 0.75;

        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }
}
