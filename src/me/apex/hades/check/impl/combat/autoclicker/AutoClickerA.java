package me.apex.hades.check.impl.combat.autoclicker;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "AutoClicker (A)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AutoClickerA extends Check {

    private int ticks;
    private double avgClickSpeed, lastCps;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION))
        {
            int ticks = this.ticks;
            this.ticks = 0;

            if(user.isDigging() || ticks > 5) return;

            double clickSpeed = ticks * 50.0D;
            avgClickSpeed = (((avgClickSpeed * 19.0D) + clickSpeed) / 20.0D);

            double cps = 1000.0D / avgClickSpeed;
            double lastCps = this.lastCps;
            this.lastCps = cps;

            if(MathUtils.isRoughlyEqual(cps, lastCps, 0.001) && cps > 2.6 && !user.isLagging())
            {
                if(vl++ > 12)
                    flag(user, "cps = " + cps + ", lastCps = " + lastCps);
            }else vl = 0;
        }else if(PacketUtils.isFlyingPacket(e.getType()))
        {
            ticks++;
        }
    }

}
