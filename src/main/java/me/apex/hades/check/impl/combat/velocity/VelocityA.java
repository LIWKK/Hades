package me.apex.hades.check.impl.combat.velocity;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.VelocityEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;

@CheckInfo(name = "Velocity", type = "A")
public class VelocityA extends Check {

    private boolean hasSent;
    private int ticksSinceSend;
    private double lastVertical;

    @Override
    public void onHandle(PacketEvent e, User user) {
        ///Just testing this code from Jonhan, may remake my own
        if(e instanceof VelocityEvent) {
            VelocityEvent event = (VelocityEvent)e;

            if(event.getVelY() > 0.2) {
                lastVertical = event.getVelY();
                ticksSinceSend = 0;
                hasSent = true;
            }
        }else if(e instanceof FlyingEvent) {
            if(hasSent) {
                ticksSinceSend++;

                int maxTicks = (int) (user.ping() / 50) + 5;

                if(ticksSinceSend <= maxTicks
                        && user.getDeltaY() <= lastVertical * 0.99
                        && elapsed(user.getTick(), user.getUnderBlockTick()) > 5
                        && !PlayerUtil.isInWeb(user.getPlayer())
                        && !PlayerUtil.isInLiquid(user.getPlayer())) {
                    if(++preVL >= maxTicks) {
                        preVL = 0;
                        flag(user, "didnt take sent velocity, v: " + user.getDeltaY());
                    }
                }else {
                    preVL = 0;
                    hasSent = false;
                }
            }
        }
    }

}
