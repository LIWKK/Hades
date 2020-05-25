package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Aura", type = "A")
public class AuraA extends Check {

    private long lastFlying;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            long timeDiff = Math.abs(e.getTimeStamp() - lastFlying);

            if (timeDiff < 5 && !user.isLagging()) {
                if (vl++ > 10)
                    flag(user, "diff = " + timeDiff);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getType())) {
            lastFlying = e.getTimeStamp();
        }
    }

}
