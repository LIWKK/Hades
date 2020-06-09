package me.apex.hades.check.impl.combat.aura;

import java.util.ArrayList;
import java.util.List;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import me.apex.hades.utils.LogUtils;
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

	private int count = 0;

	@Override
	public void onPacket(PacketReceiveEvent e, User user) {
		if(e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
			WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
			/*if((user.getDeltaYaw() <= 0.1 && user.getDeltaPitch() >= 1) || (user.getDeltaYaw() >= 1 && user.getDeltaPitch() <= 0.1)) {
				if(vl++ > 2)
					Bukkit.broadcastMessage("flag!");
			}else vl *= 0.75;*/
		}
	}
	
}
