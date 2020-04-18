package me.apex.hades.check.impl.combat.autoclicker;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "AutoClicker", type = "E")
public class AutoClickerE extends Check {

    public AutoClickerE() { setDev(true); }

    private long lastSwing, lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            long swing = e.getTimeStamp();
            long lastSwing = this.lastSwing;
            this.lastSwing = swing;

            long diff = Math.abs(swing - lastSwing);
            long lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff * Math.pow(2, 24)), (long) (diff * Math.pow(2, 24)));
            double val = gcd / Math.pow(2, 24);

            if (val > 0.0D && val < 5.0D && !user.isDigging() && !user.isLagging()) {
                flag(user, "val = " + val);
            } else vl = 0;
        }
    }

}
