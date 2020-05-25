package me.apex.hades.check.impl.combat.reach;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.funkemunky.api.utils.BoundingBox;
import cc.funkemunky.api.utils.MiscUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import org.bukkit.entity.LivingEntity;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if (!(packet.getEntity() instanceof LivingEntity)) return;
            BoundingBox entity = MiscUtils.getEntityBoundingBox((LivingEntity) packet.getEntity());

            double dist = MathUtils.getDistToHitbox(e.getPlayer(), packet.getEntity(), entity);

            if (dist > 3.075) {
                if (vl++ > 2)
                    flag(user, "dist = " + dist);
            } else vl = 0;
        }
    }

}
