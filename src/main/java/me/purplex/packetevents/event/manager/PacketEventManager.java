package me.purplex.packetevents.event.manager;

import java.util.ArrayList;

import me.purplex.packetevents.event.PacketEvent;
import me.purplex.packetevents.event.listener.PacketListener;

public interface PacketEventManager {
    public ArrayList<PacketListener> packetListeners = new ArrayList<>();
    public void callEvent(PacketEvent e);

    public void registerListener(PacketListener e);
    public boolean unregisterListener(PacketListener e);

    public boolean unregisterAllListeners();

    //public void sendPacket(Player player, WrappedPacket packet);

}
