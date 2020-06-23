package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Deque;
import java.util.LinkedList;

@CheckInfo(name = "Aura", type = "H")
public class AuraH extends Check {
    @Override
    public void init() {
        dev = true;
        enabled = true;
    }

    private final Deque<Float> angles = new LinkedList<>();

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            Entity entity = ((AttackEvent) e).getEntity();
            Vector vec = entity.getLocation().toVector().subtract(user.getPlayer().getEyeLocation().toVector());
            float angle = user.getPlayer().getEyeLocation().getDirection().angle(vec);

            angles.add(angle);

            if (angles.size() == 5) {
                double deviation = MathUtil.getStandardDeviation(angles.stream().mapToDouble(d -> d).toArray());

                double diff = user.getDeltaAngle() * deviation;

                if (diff > 11 && user.getPlayer().getLocation().toVector().setY(0.0).distance(entity.getLocation().toVector().setY(0.0)) > 1.0) {
                    if (++preVL > 4) {
                        flag(user, "high angle deviation, d: " + diff);
                    }
                } else preVL *= 0.75;

                angles.clear();
            }
        }
    }
}
