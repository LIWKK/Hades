package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Aura", type = "C")
public class AuraC extends Check{

    private int ticks;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof AttackEvent) {
            if(ticks < 1) {
                flag(user, "multiple attacks in tick, t: " + ticks);
            }
            ticks = 0;
        }else if(e instanceof FlyingPacketEvent) {
            ticks++;
        }
    }
}
