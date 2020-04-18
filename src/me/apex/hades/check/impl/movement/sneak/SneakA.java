package me.apex.hades.check.impl.movement.sneak;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInFlying;
import me.apex.hades.utils.PlayerUtils;

public class SneakA extends Check {

    public SneakA(User user) {
        super(user);
        name = "Sneak (A)";
        type = CheckType.MOVEMENT;
    }

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (e.getPacket() instanceof PacketPlayInFlying) {
                //Exceptions
                if (PlayerUtils.timeSinceOnIce(user) < 600 || PlayerUtils.hasSpeedBypass(user.getPlayer())) return;

                double dist = user.data.deltaXZ;
                double lastDist = user.data.lastDeltaXZ;

                double prediction = (lastDist + 0.12) * 0.699999988079071D;

                debug("dist = " + dist + ", expected = " + prediction);

                if (dist > prediction && user.getPlayer().isSneaking()) {
                    if (vl++ > 4)
                        flag("dist = " + dist + ", expected = " + prediction);
                } else vl = 0;
            }
        }
    }
}
