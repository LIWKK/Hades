package me.apex.hades.check.impl.combat.criticals;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;
import org.bukkit.block.BlockFace;

@CheckInfo(name = "Criticals", type = "A")
public class CriticalsA extends Check {

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid()
                    || user.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid())
                return;

            if (!user.isOnGround() && !user.getPlayer().getAllowFlight()) {
                if (user.getLocation().getY() % 1.0D == 0.0D) {
                    if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                        if (preVL++ >= 2) {
                            flag(user, "tried to hit critical on ground!");
                        }
                    }
                } else preVL = 0;
            }
        }
    }
}
