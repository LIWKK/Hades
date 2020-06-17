package me.apex.hades.check.impl.player.nofall;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

@CheckInfo(name = "NoFall", type = "A")
public class NoFallA extends Check {

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (((FlyingEvent) e).isOnGround()
                    && !user.onGround()
                    && user.location.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                if (++preVL > 2) {
                    flag(user, "Spoofed Ground, g: " + user.onGround());
                }
            } else preVL *= 0.75;
        }
    }
}
