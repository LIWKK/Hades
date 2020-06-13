package me.apex.hades.check.impl.combat.reach;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.AABB;
import me.apex.hades.utils.Ray;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {
    int preVL = 0;
    
    @Override
    public void init() {
    	dev = true;
    }

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK && packet.getEntity() instanceof Player){
                User entity = UserManager.INSTANCE.getUser(packet.getEntity().getUniqueId());
                try {
                    Ray ray = Ray.from(user);
                    double dist = AABB.from(entity).collidesD(ray,0, 10);
                    if (dist != -1) {
                    	if(dist > 3.3) {
                    	    if(++preVL >= 3){
                                flag(user, "dist = " + dist);
                                if (shouldMitigate()) e.setCancelled(true);
                            }
                    	}else preVL = 0;
                    }
                }catch (Exception ex){}
            }
        }
    }
}
