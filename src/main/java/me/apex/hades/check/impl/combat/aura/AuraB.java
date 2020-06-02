package me.apex.hades.check.impl.combat.aura;

import org.bukkit.entity.Entity;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura", type = "B")
public class AuraB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            Entity entity = packet.getEntity();

            double dir = MathUtils.getDirection(user.getLocation(), entity.getLocation());
            double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

            double range = user.getLocation().clone().toVector().setY(0.0D).distance(entity.getLocation().clone().toVector().setY(0.0D));

            double max = 32.0D;
            max += MathUtils.pingFormula(user.getPing() + 2);

            if (dist > max && range > 1.0) {
                if (vl++ > 4)
                    flag(user, "angle = " + dist);
            } else vl = 0;
        }
    }

}
