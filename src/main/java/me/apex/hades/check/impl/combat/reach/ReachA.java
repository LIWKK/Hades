package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.AABB;
import me.apex.hades.utils.Ray;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {
    int preVL = 0;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK && !user.isLagging()){
                try {
                    Ray ray = Ray.from(user.getPlayer());
                    double dist = AABB.from(packet.getEntity()).collidesD(ray,0, 10);
                    if (dist != -1) {
                    	if(dist > 3.05) {
                            //Bukkit.broadcastMessage("" + dist);
                    	}
                    }
                }catch (Exception ex){}
            }
        }
    }

}
