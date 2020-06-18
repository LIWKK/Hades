package me.apex.hades.check.impl.combat.killaura;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import org.bukkit.entity.Entity;

public class KillauraD extends Check implements ClassInterface {
    public KillauraD(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    private int ticks;
    private Entity lastTarget;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof AttackEvent) {
            Entity target = ((AttackEvent) e).getEntity();
            Entity lastTarget = this.lastTarget != null ? this.lastTarget : target;
            this.lastTarget = target;

            if(target != lastTarget) {
                if(ticks < 2) {
                    if(++preVL > 2) {
                        flag(user, "switch aura, t: " + ticks);
                    }
                }else preVL *= 0.75;
            }
            ticks = 0;
        }else if(e instanceof FlyingEvent) {
            ticks++;
        }
    }
}
