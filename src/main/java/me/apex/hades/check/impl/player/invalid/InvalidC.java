package me.apex.hades.check.impl.player.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Invalid", type = "C")
public class InvalidC extends Check {

    private int ticks;

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            int ticks = this.ticks;
            this.ticks = 0;

            if (ticks < 1) {
                flag(user, "multiple attacks in tick, t: " + ticks);
            }
        } else if (e instanceof FlyingEvent) {
            ticks++;
        }
    }

}
