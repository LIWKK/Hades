package me.apex.hades.check.impl.combat.aura;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(name = "Aura", type = "J")
public class AuraJ extends Check {
	
	@Override
	public void init() {
		dev = true;
		enabled = true;
	}
	
	public Entity lastTarget;
	public List<Float> ranges = new ArrayList<>();

	@Override
	public void onPacket(PacketReceiveEvent e, User user) {
		if(e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
			WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
			if(lastTarget != null) {
				if(lastTarget.equals(packet.getEntity())) {
					double dir = MathUtils.getDirection(user.getLocation(), lastTarget.getLocation());
		            double dist = MathUtils.getDistanceBetweenAngles360(user.getLocation().getYaw(), dir);

		            double range = user.getLocation().clone().toVector().setY(0.0D).distance(lastTarget.getLocation().clone().toVector().setY(0.0D));
		            
		            if(range < 5) {
			            if(user.getDeltaYaw() != 0.0) ranges.add(user.getDeltaYaw());
			            if(ranges.size() >= 5) {
			            	double avg = ranges.stream().mapToDouble(d -> d).average().getAsDouble();
			            	
			            	if(avg < 1) {
			            		flag(user, "avg = " + avg);
			            	}
			            	
			            	ranges.clear();
			            }
		            }
				}else ranges.clear();
				lastTarget = packet.getEntity();
			}else lastTarget = packet.getEntity();
		}
	}
	
}
