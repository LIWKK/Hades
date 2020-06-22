package me.apex.hades.check.impl.movement.step;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import me.apex.hades.util.PlayerUtil;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Step", type = "A")
public class StepA extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent){
            FlyingEvent event = (FlyingEvent)e;
            if (event.hasMoved() || !user.isInLiquid() || !user.isInWeb() || (user.getJoinTime() - time()) < 2000){
                double max = 0.7 + PlayerUtil.getPotionEffectLevel(user.getPlayer(), PotionEffectType.JUMP) * 0.2;
                if (user.getDeltaY() > max && MathUtil.isRoughlyEqual(Math.abs(user.getLastDeltaY()), 0, 0.1)){
                    flag(user, "accelerating faster than possible on Y axis. d: " + user.getDeltaY());
                }
            }
        }
    }
}
