package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import me.apex.hades.util.AABB;
import me.apex.hades.util.Ray;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check implements Listener {
    public ReachA(){
        Bukkit.getPluginManager().registerEvents(this, HadesPlugin.instance);
    }

    @Override
    public void init() {
        dev = true;
    }

    int preVL = 0;

    @Override
    public void onEvent(PacketEvent e, User user) { }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player){
            User user = UserManager.getUser((Player)event.getDamager());
            try {
                Ray ray = Ray.from(user);
                double dist = AABB.from(event.getEntity()).collidesD(ray,0, 10);
                if (dist != -1) {
                    if(dist > 3.1) {
                        if(++preVL >= 3){
                            flag(user, "dist = " + dist);
                        }
                    }else preVL = 0;
                }
            }catch (Exception ex){}
        }
    }
}
