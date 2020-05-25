package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import org.bukkit.entity.Entity;

@CheckInfo(name = "Aura", type = "C")
public class AuraC extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if (packet.getAction() == packet.getAction().ATTACK) {
                Entity entity = packet.getEntity();
                double rotation = Math.abs(user.getDeltaYaw());

                double dir = MathUtils.getDirection(user.getLocation(), entity.getLocation());
                double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

                double range = user.getLocation().clone().toVector().setY(0.0D).distance(entity.getLocation().clone().toVector().setY(0.0D));

                if (dist < 0.7 && rotation > 2) {
                    if (vl++ > 3)
                        flag(user, "angle = " + dist + ", rotation = " + rotation);
                } else vl = 0;
            }
        }
    }

}
