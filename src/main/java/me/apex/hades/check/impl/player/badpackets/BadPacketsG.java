package me.apex.hades.check.impl.player.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.event.impl.packetevents.PlaceEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "BadPackets", type = "G")
public class BadPacketsG extends Check {

    private int ticks;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof PlaceEvent) {

            int ticks = this.ticks;
            this.ticks = 0;

            if (ticks < 2 && ((PlaceEvent) e).getItemStack().getType().toString().contains("sword")) {
                if (vl++ > 4)
                    flag(user, "ticks = " + ticks);
            } else vl = 0;

        } else if (e instanceof FlyingPacketEvent) {
            ticks++;
        }
    }
}
