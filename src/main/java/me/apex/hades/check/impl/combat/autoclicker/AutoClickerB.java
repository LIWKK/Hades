package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "AutoClicker", type = "B")
public class AutoClickerB extends Check {

    private int ticks;
    private long lastSwing;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof SwingEvent){
            int ticks = this.ticks;
            this.ticks = 0;

            long lastSwing = -this.lastSwing;
            this.lastSwing = e.getTimestamp();

            long diff = e.getTimestamp() - lastSwing;

            if (ticks < 2 && diff < 50.0D) {
                if (vl++ > 2)
                    flag(user, "ticks = " + ticks + ", delay = " + diff);
            } else vl = 0;
        }else if (e instanceof FlyingEvent){
            ticks++;
        }
    }
}
