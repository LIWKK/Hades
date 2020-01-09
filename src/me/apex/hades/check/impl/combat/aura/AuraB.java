package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;
import org.bukkit.entity.Entity;

@CheckInfo(Name = "Aura (B)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AuraB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY))
        {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            Entity entity = packet.getEntity();

            double dir = MathUtils.getDirection(user.getLocation(), entity.getLocation());
            double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

            double range = user.getLocation().clone().toVector().setY(0.0D).distance(entity.getLocation().clone().toVector().setY(0.0D));

            double max = 32.0D;
            max += MathUtils.pingFormula(user.getPing() + 2);

            if(dist > max && range > 1.0 && !user.isLagging())
            {
                if(vl++ > 4)
                    flag(user, "angle = " + dist);
            }else vl = 0;
        }
    }

}
