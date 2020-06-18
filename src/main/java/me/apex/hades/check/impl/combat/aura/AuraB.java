package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Aura", type = "B")
public class AuraB extends Check {

    private long lastFlying;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof AttackEvent) {
            long timeDiff = System.currentTimeMillis() - lastFlying;
            if(timeDiff < 5) {
                if (++preVL > 10) {
                    flag(user, "low flying delay, d: " + timeDiff);
                }
            }else preVL = 0;

        }else if(e instanceof FlyingPacketEvent) {
            lastFlying = System.currentTimeMillis();
        }
    }
}

