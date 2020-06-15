package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Aura", type = "A")
public class AuraA extends Check {

    private long lastFlying;

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            long timeDiff = (System.nanoTime() / 1000000) - lastFlying;

            if (timeDiff < 5) {
                if (++threshold > 10) {
                    flag(user, "low flying delay, d: " + timeDiff);
                }
            } else threshold = 0;
        } else if (e instanceof FlyingEvent) {
            lastFlying = (System.nanoTime() / 1000000);
        }
    }

}
