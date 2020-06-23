package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

//Credits to Jonhan for original check idea!
@CheckInfo(name = "Fly", type = "B")
public class FlyB extends Check {

    @Override
    public void onHandle(PacketEvent e, User user) {
        if(e instanceof FlyingEvent) {
            double predict = (user.getLastDeltaY() - 0.08D) * 0.9800000190734863D;

            if(Math.abs(predict) <= 0.005D) {
                predict = 0;
            }

            if(elapsed(user.getTick(), user.getUnderBlockTick()) < 20) {
                if (Math.abs(predict) == 0.08307781780646571 || Math.abs(predict) == 0.04518702986887144 || user.getDeltaY() <= 0.3) {
                    return;
                }
            }

            if(elapsed(user.getTick(), user.getLiquidTick()) < 20 || user.isTakingVelocity() || elapsed(user.getTick(), user.getFlyingTick()) < 20 || elapsed(user.getTick(), user.getClimbableTick()) < 20 || user.getPlayer().getVelocity().getY() > -0.075) return;

            if(user.getAirTicks() > 6) {
                if(Math.abs(user.getDeltaY() - predict) > 1E-12) {
                    if(++preVL > 2) {
                        flag(user, "invalid dist, d: " + Math.abs(user.getDeltaY() - predict) + ", p: " + Math.abs(predict));
                    }
                }else preVL -= Math.min(preVL, 0.5);
            }
        }
    }
}
