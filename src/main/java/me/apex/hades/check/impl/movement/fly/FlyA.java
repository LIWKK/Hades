package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;
import org.bukkit.Bukkit;

public class FlyA extends Check implements ClassInterface {
    public FlyA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingEvent) {
            Bukkit.broadcastMessage("stage 5");
            double maxJump = 0.41999998688697815F;
            double deltaY = user.getTo().getY() - user.getFrom().getY();
            if (TimeUtils.elapsed(user.lastVelocity) < 1000L || deltaY <= 0.404445 && deltaY > 0.404444 && TimeUtils.elapsed(user.lastBlockJump) < 1000L || user.blockData.climbableTicks > 0 || user.getPlayer().getAllowFlight() || TimeUtils.elapsed(user.lastFullTeleport) < 1000L) {
                return;
            }
            if (user.getBlockData().halfBlockTicks > 0 || user.getBlockData().stairTicks > 0) {
                maxJump = 0.5;
            }
            if (user.getJumpPotionTicks() > 0 && user.isHasJumpPotion()) {
                maxJump = (maxJump + user.getJumpPotionMultiplyer() * 0.1F);
            }
            if (!user.isClientGround() && user.isLastClientGround() && deltaY > 0) {
                if (deltaY > maxJump || deltaY < maxJump && user.getBlockData().stairTicks < 1 && user.getBlockData().halfBlockTicks < 1 && user.getBlockData().blockAboveTicks < 1 && user.getBlockData().liquidTicks < 1) {
                    flag(user, "Jump: " + deltaY);
                }
            }
        }
    }
}
