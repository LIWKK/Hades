package me.apex.hades.listeners;

import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.processors.MovementProcessor;
import me.purplex.packetevents.event.handler.PacketHandler;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.event.impl.PacketSendEvent;
import me.purplex.packetevents.event.listener.PacketListener;
import me.purplex.packetevents.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class NetworkListener implements PacketListener, Listener {
	
	public NetworkListener() {
		Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
	}

    @PacketHandler
    public void onPacketReceive(PacketReceiveEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            //Process Movement
            MovementProcessor.INSTANCE.processMovement(e, user);

            //Check User
            if (e.getPlayer().isDead()) return;
            if (e.getTimestamp() - user.getLastJoin() < 2000) return;

            //Call Checks
            user.getChecks().stream().filter(check -> check.enabled).forEach(check -> check.onPacket(e, user));
        }
    }

    @PacketHandler
    public void onPacketSend(PacketSendEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if (e.getPacketName().equalsIgnoreCase(Packet.Server.POSITION))
                user.setTeleportTicks(5);
        }
    }

}
