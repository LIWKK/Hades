package me.apex.hades.check.impl.movement.motion;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

public class MotionC extends Check {

    public MotionC(User user) {
        super(user);
        name = "Motion (C)";
        type = CheckType.MOVEMENT;
    }

    private long b;

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (PacketUtils.isFlyingPacket(e.getPacketHandler().getType())) {
                double motionY = user.data.deltaY;
                double lastMotionY = user.data.lastDeltaY;

                //Exceptions
                if (PlayerUtils.isNearLiquid(user.getPlayer()) || PlayerUtils.isNearLiquidOffset(user.getPlayer()))
                    b = System.currentTimeMillis();
                if (PlayerUtils.hasSpeedBypass(user.getPlayer()) || PlayerUtils.isNearClimbable(user.getPlayer()))
                    return;
                if (PlayerUtils.blockNearHead(user.getPlayer()) || PlayerUtils.isHalfBlockNearHead(user.getPlayer()))
                    b = System.currentTimeMillis();
                if (System.currentTimeMillis() - b < 600 || PlayerUtils.timeSinceOnSlime(user) < 600) return;

                debug("motionY = " + motionY + ", lastMotionY = " + lastMotionY);

                if (motionY != lastMotionY && ((motionY > 0.3 && lastMotionY < -0.19) || (motionY < -0.19 && lastMotionY > 0.3))) {
                    if (vl++ > 1)
                        flag("motionY = " + motionY + ", lastMotionY = " + lastMotionY);
                } else vl = 0;
            }
        }
    }
}