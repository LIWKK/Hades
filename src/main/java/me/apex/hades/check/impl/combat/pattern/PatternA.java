package me.apex.hades.check.impl.combat.pattern;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import org.bukkit.entity.Entity;

@CheckInfo(name = "Pattern", type = "A")
public class PatternA extends Check {

    @Override
    public void init() {
        enabled = true;
    }

    private int ticks;
    private Entity lastTarget;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if(e instanceof AttackEvent) {
            Entity target = ((AttackEvent) e).getEntity();
            Entity lastTarget = this.lastTarget != null ? this.lastTarget : target;
            this.lastTarget = target;

            int ticks = this.ticks;
            this.ticks = 0;

            if(target != lastTarget) {
                if(ticks < 2) {
                    if(++threshold > 2) {
                        flag(user, "switch aura, t: " + ticks);
                    }
                }else threshold *= 0.75;
            }
        }else if(e instanceof FlyingEvent) {
            ticks++;
        }
    }

}
