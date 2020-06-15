package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import org.bukkit.entity.Entity;

@CheckInfo(name = "Aura", type = "D")
public class AuraD extends Check {

    @Override
    public void init() {
        dev = true;
        enabled = true;
    }

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            Entity entity = ((AttackEvent) e).getEntity();
            double rotation = Math.abs(user.deltaYaw);

            double dir = MathUtil.getDirection(user.location, entity.getLocation());
            double dist = MathUtil.getDistanceBetweenAngles360(user.location.getYaw(), dir);

            double range = user.location.clone().toVector().setY(0.0D).distance(entity.getLocation().clone().toVector().setY(0.0D));

            if (dist < 0.7 && rotation > 2) {
                if (++threshold > 0)
                    flag(user, "lock view, d: " + dist + ", r: " + rotation);
            } else threshold = 0;
        }
    }

}
