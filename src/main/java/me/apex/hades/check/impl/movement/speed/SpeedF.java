package me.apex.hades.check.impl.movement.speed;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;

@CheckInfo(name = "Speed", type = "F")
public class SpeedF extends Check {

	public float lastAccel = 0.0F;
	
	@Override
	public void init() {
		enabled = true;
	}
	
	@Override
	public void onPacket(PacketReceiveEvent e, User user) {
		if(PacketUtils.isFlyingPacket(e.getPacketName())) {
			float accelX = (float)(user.getLocation().getX() - user.getLastLocation().getX());
			float accelZ = (float)(user.getLocation().getZ() - user.getLastLocation().getZ());
			float accel = (accelX * accelX) + (accelZ * accelZ);
			float lastAccel = this.lastAccel;
			this.lastAccel = accel;
			
			float diff = Math.abs(accel - lastAccel) * 1000;
			
			if(diff > 5 && ((accel > 0 && lastAccel < 0) || (accel < 0 && lastAccel > 0)) && !user.onGround()) {
				flag(user, "accel = " + diff);
			}else vl = 0;
		}
	}
	
}
