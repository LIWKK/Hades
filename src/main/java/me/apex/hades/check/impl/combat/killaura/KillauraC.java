package me.apex.hades.check.impl.combat.killaura;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

public class KillauraC extends Check implements ClassInterface {
    public KillauraC(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

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
