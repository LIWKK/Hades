package me.apex.hades.check.impl.combat.reach;

import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.location.NewLocation;
import me.apex.hades.utils.math.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof AttackEvent) {
            if (((AttackEvent) e).getEntity() instanceof Player) {
                User targetUser = HadesPlugin.userManager.getUser(user.getLastEntityAttacked().getUniqueId());
                if (targetUser != null) {
                    NewLocation current = user.getLocation();
                    NewLocation past = user.getPreviousLocation();
                    double range = targetUser.locationDataQueue.stream().mapToDouble(loc -> {
                        double x = Math.min(Math.min(Math.abs(current.getX() - loc.getMinX()), Math.abs(current.getX() - loc.getMaxX())), Math.min(Math.abs(past.getX() - loc.getMinX()), Math.abs(past.getX() - loc.getMaxX())));
                        double z = Math.min(Math.min(Math.abs(current.getZ() - loc.getMinZ()), Math.abs(current.getZ() - loc.getMaxZ())), Math.min(Math.abs(past.getZ() - loc.getMinZ()), Math.abs(past.getZ() - loc.getMaxZ())));
                        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
                    }).min().orElse(0.0);

                    double maxReach = MathUtil.calculatePlayerReach(user, 3.0);
                    if (range > maxReach) {
                        flag(user, "Reach: " + range);
                    }
                }
            }
        } else if (e instanceof FlyingPacketEvent) {
            FlyingPacketEvent event = (FlyingPacketEvent)e;
            NewLocation location = user.getLocation();
            if (event.isClientMoved()) {
                if (location != null) {
                    user.locationDataQueue.add(new NewLocation(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch()));
                    user.setLocation(new NewLocation(event.getX(), event.getY(), event.getZ(),
                            event.isClientLooked() ? event.getYaw() : location.getYaw(),
                            event.isClientLooked() ? event.getPitch() : location.getPitch()));
                }
            }
            if (event.isClientLooked()) {
                if (location != null) {
                    location.setYaw(event.getYaw());
                    location.setPitch(event.getPitch());
                    user.setPreviousLocation(location);
                }
            }
        }
    }

}