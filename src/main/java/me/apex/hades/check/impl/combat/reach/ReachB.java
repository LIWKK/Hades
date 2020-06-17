package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.bukkitevents.EntityDamageByPlayerEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.BoundingBox;
import me.apex.hades.util.MiscUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach", type = "B")
public class ReachB extends Check {
    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof EntityDamageByPlayerEvent){
            EntityDamageByPlayerEvent event = (EntityDamageByPlayerEvent)e;
            if (event.getDamaged() instanceof Player){
                double reach = getDistToHitbox(user.player, event.getDamaged(), MiscUtil.getEntityBoundingBox((LivingEntity)event.getDamaged()));
                double max = HadesPlugin.instance.getConfig().getDouble("Max-ReachB");
                if (user.isSprinting) max += 0.1;
                if(reach >= max){
                    if (++preVL >= 2){
                        flag(user,"dist = " + reach);
                    }
                }else preVL = 0;
            }
        }
    }

    private double getDistToHitbox(Entity entity1, Entity entity2, BoundingBox targetBox) {
        double offset = Math.abs((targetBox.maxX - targetBox.minX) * (targetBox.maxZ - targetBox.minZ));
        double dist = Math.abs((entity1.getLocation().clone().toVector().setY(0.0D).distance(entity2.getLocation().clone().toVector().setY(0.0D))) - (offset % 3.0));
        return dist;
    }
}
