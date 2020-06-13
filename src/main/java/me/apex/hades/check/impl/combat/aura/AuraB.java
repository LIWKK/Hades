package me.apex.hades.check.impl.combat.aura;

import org.bukkit.entity.Entity;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.AABB;
import me.apex.hades.utils.Ray;

@CheckInfo(name = "Aura", type = "B")
public class AuraB extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            Entity entity = packet.getEntity();
            
            Ray ray = Ray.from(user);
            boolean lookingAt = AABB.from(entity).collides(ray, 0, 10);
            
            if(!lookingAt) {
            	//Bukkit.broadcastMessage("Attacked without looking!");
            }
        }
    }

}
