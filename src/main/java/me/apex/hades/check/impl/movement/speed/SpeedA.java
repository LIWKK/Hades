package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Speed", type = "A")
public class SpeedA extends Check {

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).hasMoved()) {
                double max = 0.29;
                max *= user.player.getWalkSpeed() / 0.2;
                max += PlayerUtil.getPotionEffectLevel(user.player, PotionEffectType.SPEED) * (max / 2);
                double diff = user.deltaXZ - user.lastDeltaXZ;
                if (diff == 0.0 && user.deltaXZ > max
                        && !user.player.getAllowFlight()
                        && elapsed(user.tick, user.teleportTick) > 20) {
                    flag(user, "consistent speed, diff: " + diff);
                }
            }
        }
    }

}
