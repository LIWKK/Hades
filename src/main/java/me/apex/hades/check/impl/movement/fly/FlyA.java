package me.apex.hades.check.impl.movement.fly;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "Fly", type = "A")
public class FlyA extends Check {

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {

            double maxJump = 0.41999998688697815F;
            double deltaY = user.getTo().getY() - user.getFrom().getY();
            if (TimeUtils.elapsed(user.lastVelocity) < 1000L || user.isWaitingForMovementVerify() || !user.isSafe() || deltaY <= 0.404445 && deltaY > 0.404444 && TimeUtils.elapsed(user.lastBlockJump) < 1000L || user.blockData.climbableTicks > 0 || user.getPlayer().getAllowFlight() || TimeUtils.elapsed(user.lastFullTeleport) < 1000L) {
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
