package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.boundingbox.AABB;
import me.apex.hades.util.boundingbox.Ray;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {

    @Override
    public void init() {
        dev = true;
    }

    boolean attacked = false;
    Entity entity;
    List<Double> reachs = new ArrayList<>();

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            attacked = true;
            entity = ((AttackEvent) e).getEntity();
        } else if (e instanceof FlyingEvent) {
            if (reachs.size() >= 10) {
                reachs.remove(0);
            }
            if (attacked) {
                attacked = false;
                Ray ray = Ray.from(user);
                double dist = AABB.from(entity).collidesD(ray, 0, 10);
                if (dist != -1) {
                    reachs.add(dist);
                }

                if (reachs.size() == 10) {
                    double total = 0;
                    double avgReach = 0;
                    for (int i = 0; i < reachs.size(); i++) {
                        total += reachs.get(i);
                        avgReach = total / reachs.size();
                    }

                    if (avgReach >= HadesPlugin.getInstance().getConfig().getDouble("Max-Reach") && dist > 2.9) {
                        if (++preVL > 1) {
                            flag(user, "hitting farther than possbile. r: " + avgReach + ", d: " + dist);
                        }
                    } else preVL = 0;
                }
            }
        }
    }
}
