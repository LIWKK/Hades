package me.apex.hades.check.impl.combat.autoclicker;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;

@CheckInfo(name = "AutoClicker", type = "C")
public class AutoClickerC extends Check {

    private long lastFlying;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
            long timeDiff = Math.abs(e.getTimestamp() - lastFlying);

            if (timeDiff < 5) {
                if (vl++ > 10)
                    flag(user, "diff = " + timeDiff);
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            lastFlying = e.getTimestamp();
        }
    }

}
