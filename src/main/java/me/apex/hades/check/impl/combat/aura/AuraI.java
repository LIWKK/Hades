package me.apex.hades.check.impl.combat.aura;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "I")
public class AuraI extends Check {
	
	public Entity lastTarget;
	public double lastRange;
	
	public double lastDiff;
	
	@Override
	public void onPacket(PacketReceiveEvent e, User user) {
		if(e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
			WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
			if(lastTarget != null) {
				if(lastTarget.equals(packet.getEntity())) {
					double dir = MathUtils.getDirection(user.getLocation(), lastTarget.getLocation());
		            double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

		            double range = user.getLocation().clone().toVector().setY(0.0D).distance(lastTarget.getLocation().clone().toVector().setY(0.0D));
		            
		            if(lastRange == 0) lastRange = range;
		            
		            if(range <= lastRange) {
		            	double distToTarget = user.getLocation().clone().toVector().setY(0.0D).distance(lastTarget.getLocation().clone().toVector().setY(0.0D));
		            	double diff = 2 * Math.PI * distToTarget / (range * user.getDeltaYaw());
		            	double lastDiff = this.lastDiff;
		            	this.lastDiff = diff;
		            	
		            	double rot = Math.abs(diff - lastDiff);
		            	
		            	if(rot > 0.0D && rot < 0.1D) {
		            		if(vl++ > 2)
		            			flag(user, "rot = " + rot);
		            	}else vl = 0;
		            }
				}else lastRange = 0;
				lastTarget = packet.getEntity();
			}else lastTarget = packet.getEntity();
		}
	}
	
}
