package me.apex.hades.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Speed", type = "B")
public class SpeedB extends Check {

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).hasMoved()) {
                double max = 0.34;
                max *= user.player.getWalkSpeed() / 0.2;
                max += PlayerUtil.getPotionEffectLevel(user.player, PotionEffectType.SPEED) * (max / 2);

                if (elapsed(user.tick, user.iceTick) < 40 || elapsed(user.tick, user.slimeTick) < 40) max += 0.34;
                if (elapsed(user.tick, user.underBlockTick) < 40) max += 0.91;
                if (elapsed(user.tick, user.velocityTick) < 40) max += 0.21;

                if (user.deltaXZ > max
                        && elapsed(user.tick, user.teleportTick) > 40
                        && !user.player.getAllowFlight()) {
                    if (++preVL > 7) {
                        flag(user, "breached limit, s: " + user.deltaXZ);
                    }
                } else preVL *= 0.75;
            }
        }
    }

}
