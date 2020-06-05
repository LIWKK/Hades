package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "AutoClicker", type = "B")
public class AutoClickerB extends Check {

    private int ticks;
    private long lastSwing;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            int ticks = this.ticks;
            this.ticks = 0;

            long lastSwing = -this.lastSwing;
            this.lastSwing = e.getTimestamp();

            long diff = e.getTimestamp() - lastSwing;

            if (ticks < 2 && diff < 50.0D) {
                if (vl++ > 2)
                    flag(user, "ticks = " + ticks + ", delay = " + diff);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            ticks++;
        }
    }

}
