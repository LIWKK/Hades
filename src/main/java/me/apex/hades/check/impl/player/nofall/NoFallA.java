package me.apex.hades.check.impl.player.nofall;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.PlayerUtil;

@CheckInfo(name = "NoFall", type = "A")
public class NoFallA extends Check {

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).isOnGround() && !user.onGround() && !PlayerUtil.isOnGround(user.player)) {
                if (++threshold > 2) {
                    flag(user, "groundspoof, g: " + user.onGround());
                }
            } else threshold = 0;
        }
    }

}
