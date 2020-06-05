package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Speed", type = "E")
public class SpeedE extends Check {

	@Override
	public void onPacket(PacketReceiveEvent e, User user) {
		if(PacketUtils.isFlyingPacket(e.getPacketName()) && !user.getPlayer().isFlying() && (System.currentTimeMillis() - user.getLastVelocity()) > 1000) {
			double diffX = Math.abs(user.getLocation().getX() - user.getLastLocation().getX());
            double diffZ = Math.abs(user.getLocation().getZ() - user.getLastLocation().getZ());
            double spd = Math.abs(diffX + diffZ);
            double vel = Math.abs(user.getPlayer().getVelocity().getX() + user.getPlayer().getVelocity().getZ());
            
            double comp = spd - vel;

            if(comp > 0.6) {
            	if(vl++ > 1) {
                	flag(user, "diff = " + comp);
            	}
            }else vl = 0;
		}
	}

}
