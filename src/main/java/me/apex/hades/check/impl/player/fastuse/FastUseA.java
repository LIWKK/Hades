package me.apex.hades.check.impl.player.fastuse;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.bukkitevents.InteractEvent;
import me.apex.hades.event.impl.bukkitevents.ItemConsumeEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MiscUtil;
import org.bukkit.Material;
import org.bukkit.event.block.Action;

@CheckInfo(name = "FastUse", type = "A")
public class FastUseA extends Check {

    private long startEat;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof InteractEvent) {
            InteractEvent interactEvent = (InteractEvent) e;
            if (interactEvent.getItem() != null) {
                if (interactEvent.getAction() == Action.RIGHT_CLICK_AIR || interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Material m = interactEvent.getItem().getType();
                    if (MiscUtil.isFood(m)) {
                        startEat = time();
                    }
                }
            }
        } else if (e instanceof ItemConsumeEvent) {
            long diff = time() - startEat;
            if (diff <= 1400) {
                flag(user, "ate food faster, t: " + diff);
            }
        }
    }
}