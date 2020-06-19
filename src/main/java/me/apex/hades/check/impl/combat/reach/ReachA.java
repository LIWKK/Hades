package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.boundingbox.AABB;
import me.apex.hades.util.boundingbox.Ray;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check implements Listener {
    public ReachA(){
        Bukkit.getPluginManager().registerEvents(this, HadesPlugin.getInstance());
    }

    @Override
    public void init() {
        dev = true;
    }

    @Override
    public void onHandle(PacketEvent e, User user) {
        if(e instanceof AttackEvent) {
            try {
                double max = HadesPlugin.getInstance().getConfig().getDouble("Max-ReachA");
                Ray ray = Ray.from(user);
                double dist = AABB.from(((AttackEvent) e).getEntity()).collidesD(ray,0, 10);
                if (user.isSprinting()) max += 0.1;
                if (dist != -1) {
                    if(dist > max) {
                        if(++preVL >= max){
                            flag(user, "dist = " + dist);
                        }
                    }else preVL = preVL > 1 ? preVL-- : 0;
                }
            }catch (Exception ex){}
        }
    }
}
