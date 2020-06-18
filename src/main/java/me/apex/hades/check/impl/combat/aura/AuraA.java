package me.apex.hades.check.impl.combat.aura;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.math.MathUtil;
import me.apex.hades.utils.time.TimeUtils;

@CheckInfo(name = "Aura", type = "A")
public class AuraA extends Check {

    double lastDist;
    int hits;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (TimeUtils.elapsed(user.lastUseEntityPacket) < 100L) {
                double deltaXZ = MathUtil.hypot(user.getTo().getX() - user.getFrom().getX(), user.getTo().getZ() - user.getFrom().getZ());
                double lastDist = this.lastDist;
                this.lastDist = deltaXZ;

                if (user.isSprinting() && ++hits <= 2 && user.isOnGround()) {
                    double accel = Math.abs(deltaXZ - lastDist);
                    if (accel < 0.027) {
                        if (preVL++ > 7) {
                            flag(user, "Keep Sprint");
                        }
                    } else {
                        preVL = 0;
                    }
                }
            }
        } else if (e instanceof AttackEvent) {
            hits = 0;
        }
    }
}
