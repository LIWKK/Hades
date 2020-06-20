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
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).isOnGround() && elapsed(user.getTick(), user.getServerGroundTick()) > 20 && user.getTick() > 10 & !user.isOnGround()) {
                flag(user, "Spoofed Ground, g: " + !PlayerUtil.isOnGround(user.getPlayer()));
            }
        }
    }
}
