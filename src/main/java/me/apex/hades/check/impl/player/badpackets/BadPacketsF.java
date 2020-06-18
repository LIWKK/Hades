package me.apex.hades.check.impl.player.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "BadPackets", type = "F")
public class BadPacketsF extends Check {
    private boolean lastWasArm;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof AttackEvent) {
            if (!lastWasArm) {
                if (vl++ > 1)
                    flag(user, "swung = " + lastWasArm);
            } else vl = 0;
        } else if (e instanceof SwingEvent) {
            lastWasArm = true;
        } else if (e instanceof FlyingPacketEvent) {
            lastWasArm = false;
        }
    }
}
