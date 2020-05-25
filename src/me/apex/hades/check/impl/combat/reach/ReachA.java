package me.apex.hades.check.impl.combat.reach;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.WrappedPacketPlayInUseEntity;

import org.bukkit.entity.LivingEntity;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedPacketPlayInUseEntity packet = new WrappedPacketPlayInUseEntity(e.getPacket());
            if (!(packet.entity instanceof LivingEntity)) return;
            //BoundingBox entity = MiscUtils.getEntityBoundingBox((LivingEntity) packet.getEntity());

            //double dist = MathUtils.getDistToHitbox(e.getPlayer(), packet.getEntity(), entity);

            //if (dist > 3.075) {
            //    if (vl++ > 2)
             //       flag(user, "dist = " + dist);
            //} else vl = 0;
        }
    }

}
