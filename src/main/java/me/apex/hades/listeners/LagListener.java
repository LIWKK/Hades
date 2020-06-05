package me.apex.hades.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.github.retrooper.packetevents.event.PacketHandler;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

public class LagListener implements PacketListener, Listener {
	
	public LagListener() {
		Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
	}

    @PacketHandler
    public void onPacketReceive(PacketReceiveEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if (PacketUtils.isFlyingPacket(e.getPacketName())) {
                double diff = e.getTimestamp() - user.getLastPacket();
                double value = 50;
                value -= diff;

                if (value < -80) {
                    double lastLag = e.getTimestamp() - user.getLastLagPacket();

                    if (lastLag < 900) {
                        user.setLagging(true);
                        user.setLastLagSet(e.getTimestamp());
                    } else
                        user.setLagging(false);

                    user.setLastLagPacket(e.getTimestamp());
                }

                if (e.getTimestamp() - user.getLastLagSet() > 1000)
                    user.setLagging(false);

                user.setLastPacket(e.getTimestamp());
                PacketUtils.sendTransaction(user);
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.KEEP_ALIVE)) {
                user.setLastKeepAlive(e.getTimestamp());
                user.setPing((int) Math.abs(e.getTimestamp() - user.getLastServerKeepAlive()));
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.TRANSACTION)) {
                user.getTransactionQueue().add(e.getTimestamp());
                if (user.getTransactionQueue().size() == 50) {
                    double deviation = MathUtils.getStandardDeviation(user.getTransactionQueue().stream().mapToLong(l -> l).toArray());

                    if (deviation <= 720 && user.isLagging())
                        user.setLagging(false);

                    user.getTransactionQueue().clear();
                }
            }
        }
    }

    @PacketHandler
    public void onPacketSend(PacketSendEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if (e.getPacketName().equalsIgnoreCase(Packet.Server.KEEP_ALIVE))
                user.setLastServerKeepAlive(e.getTimestamp());
        }
    }

}
