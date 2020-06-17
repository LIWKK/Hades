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
    public void onEvent(PacketEvent e, User user) {
        ///Just testing this code from Jonhan, in works on remaking my own check based on this
        /*if(e instanceof VelocityEvent) {
            VelocityEvent event = (VelocityEvent)e;

            if(event.getVelocityY() > 0.2) {
                lastVertical = event.getVelocityY();
                ticksSinceSend = 0;
                hasSent = true;
            }
        }else if(e instanceof FlyingEvent) {
            if(hasSent) {
                ticksSinceSend++;

                int maxTicks = (int) (user.ping() / 50) + 5;

                if(ticksSinceSend <= maxTicks
                        && user.deltaY <= lastVertical * 0.99
                        && elapsed(user.tick, user.underBlockTick) > 5
                        && !PlayerUtil.isInWeb(user.player)
                        && !PlayerUtil.isInLiquid(user.player)) {
                    if(++preVL >= maxTicks) {
                        preVL = 0;
                        flag(user, "didnt take sent velocity, v: " + user.deltaY);
                    }
                }else {
                    preVL = 0;
                    hasSent = false;
                }
            }
        }*/
    }

}
