package me.apex.hades.check.impl.combat.autoclicker;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
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
        if (e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            int ticks = this.ticks;
            this.ticks = 0;

            long lastSwing = -this.lastSwing;
            this.lastSwing = e.getTimeStamp();

            long diff = e.getTimeStamp() - lastSwing;

            if (ticks < 2 && diff < 50.0D && !user.isLagging()) {
                if (vl++ > 2)
                    flag(user, "ticks = " + ticks + ", delay = " + diff);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getType())) {
            ticks++;
        }
    }

}
