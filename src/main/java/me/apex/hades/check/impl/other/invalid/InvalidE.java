package me.apex.hades.check.impl.other.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.user.User;

@CheckInfo(name = "Invalid", type = "E")
public class InvalidE extends Check {
    @Override
    public void init() {
        dev = true;
    }

    @Override
    public void onHandle(PacketEvent e, User user) {
        if(user.isSprinting() && user.getDeltaXZ() <= 0.01 && user.getSprintingTicks() > 5 && !user.isInWeb() && !user.isOnGround() && !user.isOnClimbableBlock()) {
            if(++preVL > 6) {
                flag(user, "sprinting with invalid motion, d: " + user.getDeltaXZ());
            }
        }else preVL = 0;
    }
}
