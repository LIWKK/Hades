package me.apex.hades.check.impl.other.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import org.bukkit.Difficulty;

@CheckInfo(name = "Invalid", type = "D")
public class InvalidD extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if(e instanceof FlyingEvent) {
            if (user.getLocation().getWorld().getDifficulty() == Difficulty.PEACEFUL) return;
            if (user.isSprinting() && user.getPlayer().getFoodLevel() < 6) {
                flag(user, "sprinting while hunger levels are low.");
            }
        }
    }
}
