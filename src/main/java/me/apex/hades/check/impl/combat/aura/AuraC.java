package me.apex.hades.check.impl.combat.aura;

import org.bukkit.entity.Entity;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura", type = "C")
public class AuraC extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK) {
                Entity entity = packet.getEntity();
                double rotation = Math.abs(user.getDeltaYaw());

                double dir = MathUtils.getDirection(user.getLocation(), entity.getLocation());
                double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

                double range = user.getLocation().clone().toVector().setY(0.0D).distance(entity.getLocation().clone().toVector().setY(0.0D));

                if (dist < 0.7 && rotation > 2) {
                    if (vl++ > 0)
                        flag(user, "angle = " + dist + ", rotation = " + rotation);
                } else vl = 0;
            }
        }
    }

}
