package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;

public class FlyD extends Check implements ClassInterface {
    public FlyD(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (user != null) {
                if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.isWaitingForMovementVerify() || !user.isSafe() || user.getBlockData().climbableTicks > 0 || user.getBlockData().liquidTicks > 0|| TimeUtils.elapsed(user.getLastFullTeleport()) < 1000L) {
                    return;
                }
                double deltaY = user.getTo().getY() - user.getFrom().getY();
                double max = 0.41999998688697815F;
                if (user.getBlockData().halfBlockTicks > 0 || user.getBlockData().stairTicks > 0 || user.getBlockData().fenceTicks > 0 || user.getBlockData().wallTicks > 0) {
                    max = 0.5;
                }
                if (user.getJumpPotionTicks() > 0 && user.isHasJumpPotion()) {
                    max = (max + user.getJumpPotionMultiplyer() * 0.1F);
                }
                if (deltaY > max) {
                    flag(user, "Max Speed Up: "+max);
                }
            }
        }
    }
}