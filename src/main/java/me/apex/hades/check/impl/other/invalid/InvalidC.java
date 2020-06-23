package me.apex.hades.check.impl.other.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.AttackEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "Invalid", type = "C")
public class InvalidC extends Check {
    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof AttackEvent) {
            if (((AttackEvent) e).getEntity().getEntityId() == user.getPlayer().getEntityId()) {
                flag(user, "tried to attack themself!");
            }
        }
    }

}
